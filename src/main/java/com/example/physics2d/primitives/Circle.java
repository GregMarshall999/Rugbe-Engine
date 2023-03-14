package com.example.physics2d.primitives;

import com.example.physics2d.rigidbody.Rigidbody2D;
import org.joml.Vector2f;

public class Circle
{
    private float radius = 1.0f;
    private Rigidbody2D rigidbody = null;

    public float getRadius() {
        return radius;
    }

    public Vector2f getCenter() {
        return rigidbody.getPosition();
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setRigidbody(Rigidbody2D rigidbody) {
        this.rigidbody = rigidbody;
    }
}
