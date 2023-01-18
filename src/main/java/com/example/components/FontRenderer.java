package com.example.components;

public class FontRenderer extends Component
{
    @Override
    public void start() {
        if(gameObject.getComponents(SpriteRenderer.class) != null)
            System.out.println("Found Font Renderer!");
    }

    @Override
    public void update(float dt) {

    }
}
