package application;

import entities.Missile;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Program extends PApplet {
    
    public static int width, height, frameCount = 0;
    public static boolean completed;
    
    private PVector target;
    private PVector[] obstacle;
    private List<Missile> generation;
    private int populationSize = 100, generationCount = 1;
    
    
    public static void main(String[] args) {
        PApplet.main("application.Program");
    }
    
    @Override
    public void settings() {
        
        width = 600;
        height = 600;
        
        size(width, height);
        
        target = new PVector(width / 2, 100);
        
        obstacle = new PVector[2];
        obstacle[0] = new PVector(width / 2 - 200, height / 2 + 13);
        obstacle[1] = new PVector(width / 2 + 200, height / 2 - 13);
        
        generation = new ArrayList<>();
        
        for (int i = 0; i < populationSize; i++) {
            
            generation.add(new Missile(200, 5, obstacle, target));
        }
    }
    
    @Override
    public void draw() {
        frameCount++;
        
        if (frameCount % 200 == 0) {
            repopulate();
        }
        update();
    }
    
    private void update() {
        background(21);
        noStroke();
        fill(255);
        textSize(20);
        textAlign(LEFT, CENTER);
        text("generation - " + generationCount, 15, 25);
        text("population - " + populationSize, 15, 50);
        if (!completed)
            fill(255, 0, 0);
        else
            fill(0, 255, 0);
        text("completed - " + completed, 15, 75);
        fill(51);
        rectMode(CENTER);
        rect(width / 2, height / 2, 400, 26);
        fill(255, 0, 0);
        ellipse(target.x, target.y, 50, 50);
        fill(255);
        ellipse(target.x, target.y, 35, 35);
        fill(255, 0, 0);
        ellipse(target.x, target.y, 15, 15);
        for (Missile m : generation) {
            showMissile(m);
        }
    }
    
    private void repopulate() {
        float sum = 0;
        for (Missile m : generation) {
            m.calculateFitness();
            sum += m.getFitness();
        }
        
        //it becomes a percentage of the total fitness of the generation
        for (Missile m : generation) {
            m.setFitness(m.getFitness() / sum);
        }
        
        //repopulating
        List<Missile> temp = new ArrayList<>();
        
        for (int i = 0; i < populationSize; i++) {
            temp.add(new Missile(generation, 200, 5, obstacle, target));
        }
        
        generation = temp;
        generationCount++;
        completed = false;
    }
    
    private void showMissile(Missile missile) {
        
        fill(255, 100);
        //rotates the Missile to its velocity angle
        pushMatrix();
        missile.move();
        translate(missile.getPos().x, missile.getPos().y);
        rotate(-(HALF_PI - missile.getAngle()));
        rect(0, -16, 8, 40);
        //draws a "head"
        fill(missile.getColor());
        rect(0, 0, 8, 8);
        popMatrix();
    }
}
