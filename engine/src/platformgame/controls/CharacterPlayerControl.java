/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package platformgame.controls;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;

/**
 *
 * @author Vortex
 */
public class CharacterPlayerControl extends RigidBodyControl implements PhysicsTickListener, PhysicsCollisionListener   {
    private PhysicsSpace physicsSpace;
    
    public CharacterPlayerControl(PhysicsSpace physicsSpace)    {
        this.physicsSpace = physicsSpace;

        //getPhysicsSpace().addTickListener(this);
        //getPhysicsSpace().addCollisionListener(this);
    }
    
    public void prePhysicsTick(PhysicsSpace space, float f){
      // apply state changes ...
    }

    public void physicsTick(PhysicsSpace space, float f){
      // poll game state ...
    }

    public void collision(PhysicsCollisionEvent event) {
        System.out.println("NodeA: " + event.getNodeA().getName());
        System.out.println("NodeB: " + event.getNodeB().getName());
        
        if ("Box".equals(event.getNodeA().getName()) || "Box".equals(event.getNodeB().getName())) {
            if ("bullet".equals(event.getNodeA().getName()) || "bullet".equals(event.getNodeB().getName())) {
                //fpsText.setText("You hit the box!");
            }
        }

        if ("+player_1-ogremesh".equals(event.getNodeA().getName()) && "simple_enemy_1-ogremesh".equals(event.getNodeB().getName()) || 
                "simple_enemy_1-ogremesh".equals(event.getNodeA().getName()) && "+player_1-ogremesh".equals(event.getNodeB().getName()))
        {
            System.out.println("Collison: Player-SimpleEnemy");
            //Place Player1 and SimpleEnemy1 back, so they are not colliding !!!!!!!!!!!!
            if("simple_enemy_1-ogremesh".equals(event.getNodeA().getName()))    {
                //event.getNodeA().move(event.getNodeA().getLocalRotation().multLocal(new Vector3f(0,0,1)).multLocal(-tpf));
                event.getNodeA().move(event.getNodeA().getLocalRotation().multLocal(new Vector3f(0,0,1)).multLocal(-16));

                // Move SimpleEnemy1 away with physics
                //event.getNodeA().getControl(CharacterControl.class).warp(event.getNodeA().getLocalRotation().multLocal(new Vector3f(0,0,1)).multLocal(-16));
            }
            if("simple_enemy_1-ogremesh".equals(event.getNodeB().getName()))    {
                //event.getNodeA().move(event.getNodeA().getLocalRotation().multLocal(new Vector3f(0,0,1)).multLocal(-tpf));
                event.getNodeB().move(event.getNodeB().getLocalRotation().multLocal(new Vector3f(0,0,1)).multLocal(-16));

                // Move SimpleEnemy1 away with physics
                //event.getNodeB().getControl(CharacterControl.class).warp(event.getNodeB().getLocalRotation().multLocal(new Vector3f(0,0,1)).multLocal(-16));
            }
            
        }

    }
}
