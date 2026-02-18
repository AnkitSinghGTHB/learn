
/**
 * Shape Hierarchy — Week 4 Hands-On Project #1
 * 
 * Demonstrates:
 * - Abstract classes (Shape)
 * - Interfaces (Drawable, Resizable)
 * - Inheritance & Polymorphism
 * - Method overriding
 * - Comparable for sorting
 * - Collections (ArrayList, sorting)
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ──────────────────────────────────────────
// INTERFACES
// ──────────────────────────────────────────

interface Drawable {
    void draw();
}

interface Resizable {
    void resize(double factor);
}

// ──────────────────────────────────────────
// ABSTRACT BASE CLASS
// ──────────────────────────────────────────

abstract class Shape implements Comparable<Shape>, Drawable {
    private String color;
    private boolean filled;

    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    // Abstract methods — subclasses MUST implement
    public abstract double area();

    public abstract double perimeter();

    // Concrete methods — inherited by all shapes
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    // Natural ordering: by area ascending
    @Override
    public int compareTo(Shape other) {
        return Double.compare(this.area(), other.area());
    }

    @Override
    public String toString() {
        return String.format("%s [color=%s, filled=%s, area=%.2f, perimeter=%.2f]",
                getClass().getSimpleName(), color, filled, area(), perimeter());
    }
}

// ──────────────────────────────────────────
// CONCRETE CLASSES
// ──────────────────────────────────────────

class Circle extends Shape implements Resizable {
    private double radius;

    public Circle(String color, boolean filled, double radius) {
        super(color, filled);
        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be positive");
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a " + getColor() + " circle with radius " + radius);
        // Simple ASCII art
        int r = (int) Math.min(radius, 10);
        for (int y = -r; y <= r; y++) {
            for (int x = -r; x <= r; x++) {
                if (x * x + y * y <= r * r) {
                    System.out.print(isFilled() ? "█" : "○");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public void resize(double factor) {
        if (factor <= 0)
            throw new IllegalArgumentException("Factor must be positive");
        this.radius *= factor;
        System.out.println("Circle resized. New radius: " + radius);
    }
}

class Rectangle extends Shape implements Resizable {
    private double width;
    private double height;

    public Rectangle(String color, boolean filled, double width, double height) {
        super(color, filled);
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("Dimensions must be positive");
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public void draw() {
        System.out.println("Drawing a " + getColor() + " rectangle (" + width + " x " + height + ")");
        int w = (int) Math.min(width, 20);
        int h = (int) Math.min(height, 10);
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (isFilled() || row == 0 || row == h - 1 || col == 0 || col == w - 1) {
                    System.out.print("█");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public void resize(double factor) {
        if (factor <= 0)
            throw new IllegalArgumentException("Factor must be positive");
        this.width *= factor;
        this.height *= factor;
        System.out.println("Rectangle resized. New dimensions: " + width + " x " + height);
    }
}

class Triangle extends Shape {
    private double sideA, sideB, sideC;

    public Triangle(String color, boolean filled, double a, double b, double c) {
        super(color, filled);
        if (a <= 0 || b <= 0 || c <= 0)
            throw new IllegalArgumentException("Sides must be positive");
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Invalid triangle: sides don't form a valid triangle");
        }
        this.sideA = a;
        this.sideB = b;
        this.sideC = c;
    }

    // Equilateral triangle convenience constructor
    public Triangle(String color, boolean filled, double side) {
        this(color, filled, side, side, side);
    }

    @Override
    public double area() {
        // Heron's formula
        double s = (sideA + sideB + sideC) / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }

    @Override
    public double perimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a " + getColor() + " triangle (sides: "
                + sideA + ", " + sideB + ", " + sideC + ")");
        int h = (int) Math.min(sideA, 8);
        for (int row = 1; row <= h; row++) {
            // Indent
            for (int space = 0; space < h - row; space++)
                System.out.print(" ");
            // Stars
            for (int star = 0; star < 2 * row - 1; star++) {
                if (isFilled() || star == 0 || star == 2 * row - 2 || row == h) {
                    System.out.print("▲");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}

// ──────────────────────────────────────────
// MAIN — Demo & Testing
// ──────────────────────────────────────────

public class ShapeDemo {
    public static void main(String[] args) {

        // 1. Create shapes using polymorphism
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle("Red", true, 5));
        shapes.add(new Rectangle("Blue", false, 4, 6));
        shapes.add(new Triangle("Green", true, 3, 4, 5));
        shapes.add(new Circle("Yellow", true, 3));
        shapes.add(new Rectangle("Purple", true, 10, 2));

        // 2. Display all shapes (polymorphism in action)
        System.out.println("════════════════════════════════════");
        System.out.println("   ALL SHAPES");
        System.out.println("════════════════════════════════════");
        for (Shape s : shapes) {
            System.out.println(s);
        }

        // 3. Draw each shape (interface polymorphism)
        System.out.println("\n════════════════════════════════════");
        System.out.println("   DRAWING SHAPES");
        System.out.println("════════════════════════════════════");
        for (Shape s : shapes) {
            s.draw();
            System.out.println("---");
        }

        // 4. Sort by area (Comparable)
        Collections.sort(shapes);
        System.out.println("\n════════════════════════════════════");
        System.out.println("   SORTED BY AREA (ascending)");
        System.out.println("════════════════════════════════════");
        for (Shape s : shapes) {
            System.out.printf("  %-30s → Area: %.2f%n",
                    s.getClass().getSimpleName() + " (" + s.getColor() + ")", s.area());
        }

        // 5. Resize (only Resizable shapes)
        System.out.println("\n════════════════════════════════════");
        System.out.println("   RESIZING (Resizable shapes only)");
        System.out.println("════════════════════════════════════");
        for (Shape s : shapes) {
            if (s instanceof Resizable r) {
                System.out.print("  Before: area = " + String.format("%.2f", s.area()) + " → ");
                r.resize(2);
                System.out.println("  After:  area = " + String.format("%.2f", s.area()));
            } else {
                System.out.println("  " + s.getClass().getSimpleName() + " is not Resizable");
            }
        }

        // 6. Total area calculation
        double totalArea = 0;
        for (Shape s : shapes) {
            totalArea += s.area();
        }
        System.out.printf("\nTotal area of all shapes: %.2f%n", totalArea);
    }
}
