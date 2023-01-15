package com.example.engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera
{
    private Matrix4f projectionMatrix, viewMatrix, inverseProjection, inverseView;
    private Vector2f position;

    public Camera(Vector2f position) {
        this.position = position;
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        inverseProjection = new Matrix4f();
        inverseView = new Matrix4f();
        adjustProjection();
    }

    public void translate(Vector2f direction) {
        position.x += direction.x;
        position.y += direction.y;
    }

    public void adjustProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(0f, 32f*40f, 0f, 32f*21f, 0f, 100f);
        projectionMatrix.invert(inverseProjection);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0f, 0f, -1f);
        Vector3f cameraUp = new Vector3f(0f, 1f, 0f);
        viewMatrix.identity();
        viewMatrix = viewMatrix.lookAt(
                new Vector3f(position.x, position.y, 20f),
                cameraFront.add(position.x, position.y, 0f),
                cameraUp);

        viewMatrix.invert(inverseView);
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getInverseProjection() {
        return inverseProjection;
    }

    public Matrix4f getInverseView() {
        return inverseView;
    }
}
