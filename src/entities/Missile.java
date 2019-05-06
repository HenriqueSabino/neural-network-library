package entities;

import application.Program;
import entities.util.Utils;
import io.github.henriquesabino.neunet.ga.GANeuralNetwork;
import processing.core.PVector;

import java.util.List;

public class Missile {
    
    private PVector pos, vel;
    private int moveQuantity, speed, color;
    private float fitness, angle, multiplier;
    private boolean dead = false, winner = false, changedCol = false;
    
    private PVector[] obstacle;
    private PVector target;
    
    private GANeuralNetwork brain;
    
    public Missile(int moveQuantity, int speed, PVector[] obstacle, PVector target) {
        
        this.moveQuantity = moveQuantity;
        this.speed = speed;
        
        //negative goes upwards
        vel = new PVector(0, -1);
        pos = new PVector(Program.width / 2, Program.height - 50);
        color = randomColor();
        
        this.obstacle = obstacle;
        this.target = target;
        
        //2 points for the obstacle, 1 point for the target,
        //2 vectors for the position and velocity
        
        //2 outputs that will be the vector of the thrust
        brain = new GANeuralNetwork(10, new int[]{8, 4}, 2, 0.01);
    }
    
    public Missile(List<Missile> prevGen, int moveQuantity, int speed, PVector[] obstacle, PVector target) {
        this.speed = speed;
        
        //negative goes upwards
        vel = new PVector(0, -1);
        pos = new PVector(Program.width / 2, Program.height - 50);
        
        this.obstacle = obstacle;
        this.target = target;
        
        Missile parent = poolSelection(prevGen);
        brain = new GANeuralNetwork(parent.getBrain().copy());
        setColorBasedOnParents(parent.getColor());
    }
    
    public void calculateFitness() {
        fitness = 1 / (Utils.sq(Utils.dist(pos.x, pos.y, target.x, target.y)));
        if (dead)
            fitness /= 100;
        if (winner)
            fitness *= multiplier;
    }
    
    public void move() {
        
        checkState();
        if (!winner && !dead) {
            
            double[] inputs = new double[]{obstacle[0].x, obstacle[0].y, obstacle[1].x, obstacle[1].y,
                    target.x, target.y, pos.x, pos.y, vel.x, vel.y};
            
            double[] outputs = brain.predict(inputs);
            
            //mapping the outputs from 0 to 1 to -1 to 1
            PVector thrust = new PVector((float) outputs[0] * 2 - 1, (float) outputs[1] * 2 - 1);
            
            vel.add(thrust).setMag(speed);
            pos.add(vel);
            angle = vel.heading();
        }
    }
    
    private void checkState() {
        if ((pos.x >= obstacle[0].x && pos.y <= obstacle[0].y)) {
            if ((pos.x <= obstacle[1].x && pos.y >= obstacle[1].y)) {
                dead = true;
            }
        }
        if (Utils.dist(pos.x, pos.y, target.x, target.y) <= 28) {
            winner = true;
            multiplier = 400 / (Program.frameCount % 200);
            Program.completed = true;
        }
    }
    
    private int randomColor() {
        int a = 255;
        int r = Utils.floor(Utils.random(255));
        int g = Utils.floor(Utils.random(255));
        int b = Utils.floor(Utils.random(255));
        
        return a << 24 | r << 16 | g << 8 | b;
    }
    
    private void setColorBasedOnParents(int parentsColor) {
        int a = 255;
        int r = (Utils.floor(Utils.random(255)) + (parentsColor >> 16) & 0xFF) / 2;
        int g = (Utils.floor(Utils.random(255)) + (parentsColor >> 8) & 0xFF) / 2;
        int b = (Utils.floor(Utils.random(255)) + parentsColor & 0xFF) / 2;
        
        setColor(a << 24 | r << 16 | g << 8 | b);
    }
    
    private Missile poolSelection(List<Missile> prevGen) {
        // Start at 0
        int index = 0;
        
        // Pick a random number between 0 and 1
        float random = Utils.random(1);
        
        // Keep subtracting probabilities until you get less than zero
        // Higher probabilities will be more likely to be fixed since they will
        // subtract a larger number towards zero
        while (random > 0) {
            random -= prevGen.get(index).getFitness();
            // And move on to the next
            index++;
        }
        
        // Go back one
        index--;
        return prevGen.get(index);
    }
    
    public PVector getPos() {
        return pos;
    }
    
    public void setPos(PVector pos) {
        this.pos = pos;
    }
    
    public float getFitness() {
        return fitness;
    }
    
    public void setFitness(float fitness) {
        this.fitness = fitness;
    }
    
    public float getAngle() {
        return angle;
    }
    
    public void setAngle(float angle) {
        this.angle = angle;
    }
    
    public int getColor() {
        return color;
    }
    
    public void setColor(int color) {
        this.color = color;
    }
    
    private GANeuralNetwork getBrain() {
        return brain;
    }
}