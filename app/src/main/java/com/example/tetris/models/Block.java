package com.example.tetris.models;

import android.graphics.Point;
import androidx.annotation.NonNull;
import com.example.tetris.enums.BlockColor;
import com.example.tetris.enums.FieldConstants;
import com.example.tetris.enums.Shape;

import java.util.Random;

public class Block {
    private int shapeIndex;
    private int frameNumber;
    private BlockColor color;
    private Point position;

    private Block(int shapeIndex, BlockColor blockColor) {
        this.frameNumber = 0;
        this.shapeIndex = shapeIndex;
        this.color = blockColor;
        this.position = new Point(FieldConstants.COLUMN_COUNT.getValue() / 2, 0);
    }

    public static Block createBlock() {
        Random random = new Random();
        int shapeIndex = random.nextInt(Shape.values().length);
        BlockColor blockColor = BlockColor.values()[random.nextInt(BlockColor.values().length)];
        Block block = new Block(shapeIndex, blockColor);
        block.position.x = block.position.x - Shape.values()[shapeIndex].getStartPosition();

        return block;
    }

    public static int getColor(byte value) {
        for (BlockColor color : BlockColor.values()) {
            if (value == color.getByteValue()) {
                return color.getRgbValue();
            }
        }

        return -1;
    }

    public final void setState(int frame, Point position) {
        this.frameNumber = frame;
        this.position = position;
    }

    @NonNull
    public final byte[][] getShape(int frameNumber) {
        return Shape.values()[shapeIndex].getFrame(frameNumber).as2dByteArray();
    }

    public Point getPosition() {
        return position;
    }

    public final int getFrameCount() {
        return Shape.values()[shapeIndex].getFrameCount();
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public int getColor() {
        return color.getRgbValue();
    }

    public byte getStaticValue() {
        return color.getByteValue();
    }
}
