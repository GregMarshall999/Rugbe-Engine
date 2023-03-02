package com.example.physics2d.primitives;

import com.example.physics2d.rigidbody.Rigidbody2D;
import org.joml.Vector2f;

public class Circle
{
    private float radius = 1.0f;
    private Rigidbody2D body = null;

    public float getRadius() {
        return radius;
    }

    public Vector2f getCenter() {
        return body.getPosition();
    }
}
