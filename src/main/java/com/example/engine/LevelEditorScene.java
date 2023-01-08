package com.example.engine;

import com.example.renderer.Shader;
import com.example.renderer.Texture;
import com.example.util.Time;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene
{
    private int vertexId, fragmentId, shaderProgram, vaoId, vboId, eboId;

    private Shader defaultShader;
    private Texture testTexture;

    private float[] vertexArray = {
        //position          //color             //UV Coordinates
        100f, 0f,   0f,     1f, 0f, 0f, 1f,     1, 1, //bottom right
        0f,   100f, 0f,     0f, 1f, 0f, 1f,     0, 0, //top left
        100f, 100f, 0f,     0f, 0f, 1f, 1f,     1, 0, //top right
        0f,   0f,   0f,     1f, 1f, 0f, 1f,     0, 1  //bottom left
    };

    //IMPORTANT Triangle vertex order in counter-clockwise order
    private int[] elementArray = {
        2, 1, 0, //top right triangle
        0, 1, 3  //bottom left triangle
    };

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        camera = new Camera(new Vector2f());

        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();
        testTexture = new Texture("assets/images/testImage.jpg");


        //Generate VAO, VBO, EBO and send to GPU
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        //Create float buffer vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        int positionSize = 3;
        int colorSize = 4;
        int uvSize = 2;
        int vertexSizeBytes = (positionSize + colorSize + uvSize)*Float.BYTES;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize*Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionSize + colorSize)*Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    @Override
    public void update(float dt) {
        //Vector2f currentPosition = camera.getPosition();
        //camera.adjustPositionTo(new Vector2f(currentPosition.x - dt*50f, currentPosition.y - dt*20f));

        defaultShader.use();

        //upload texture to shader
        defaultShader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        testTexture.bind();

        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());

        //bind VAO
        glBindVertexArray(vaoId);

        //enable vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        //unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        defaultShader.detach();
    }
}
