package com.example.renderer;

import com.example.components.SpriteRenderer;
import com.example.engine.Window;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderBatch
{
    //Vertex
    //Pos               Color
    //float, float,     float, float, float, float

    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;

    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE*Float.BYTES;
    private final int VERTEX_SIZE = 6;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE*Float.BYTES;

    private SpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;

    private int vaoId, vboId;
    private int maxBatchSize;
    private Shader shader;

    public RenderBatch(int maxBatchSize) {
        this.maxBatchSize = maxBatchSize;
        shader = new Shader("assets/shaders/default.glsl");
        shader.compile();
        sprites = new SpriteRenderer[maxBatchSize];

        //4 vertices quads
        vertices = new float[maxBatchSize*4*VERTEX_SIZE];

        numSprites = 0;
        hasRoom = true;
    }

    public void start() {
        //Generate and bind vertex array object
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        //Allocate space for vertices
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, (long)vertices.length*Float.BYTES, GL_DYNAMIC_DRAW);

        int eboId = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);
    }

    public void addSprite(SpriteRenderer spr) {
        //Add render object
        int index = numSprites;
        sprites[index] = spr;
        numSprites++;

        //Add properties to array
        loadVertexProperties(index);

        if(numSprites >= maxBatchSize)
            hasRoom = false;
    }

    public void render() {
        //rebuffer all data every frame
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        //use Shader
        shader.use();
        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());

        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, numSprites*6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        shader.detach();
    }

    private int[] generateIndices() {
        //6 indices per quad
        int[] elements = new int[6*maxBatchSize];
        for(int i = 0; i < maxBatchSize; i++)
            loadElementIndices(elements, i);

        return elements;
    }

    private void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6*index;
        int offset = 4*index;

        //Triangle 1
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset;

        elements[offsetArrayIndex + 3] = offset + 0;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

    private void loadVertexProperties(int index) {
        SpriteRenderer sprite = sprites[index];

        int offset = index*4*VERTEX_SIZE;
        Vector4f color = sprite.getColor();

        float xAdd = 1f;
        float yAdd = 1f;
        for(int i = 0; i < 4; i++) {
            switch (i) {
                case 1 -> yAdd = 0f;
                case 2 -> xAdd = 0f;
                case 3 -> yAdd = 1f;
            }

            //load position
            vertices[offset] = sprite.getGameObject().getTransform().getPosition().x +
                    (xAdd*sprite.getGameObject().getTransform().getScale().x);
            vertices[offset + 1] = sprite.getGameObject().getTransform().getPosition().y +
                    (yAdd*sprite.getGameObject().getTransform().getScale().y);

            //load color
            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            offset += VERTEX_SIZE;
        }
    }

    public boolean hasRoom() {
        return hasRoom;
    }
}
