package com.example.physics2d.forces;

import com.example.physics2d.rigidbody.Rigidbody2D;

public interface ForceGenerator
{
    void updateForce(Rigidbody2D body, float dt);
}
