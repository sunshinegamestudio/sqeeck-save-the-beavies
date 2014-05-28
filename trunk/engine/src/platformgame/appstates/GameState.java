/*
CarGame, a game where you can drive with a car.
Copyright (C) 2010  Vortex GameStudio

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package platformgame.appstates;

import platformgame.entities.Terrain;
import platformgame.entities.Sky;
import platformgame.entities.Sun;
import platformgame.entities.SimpleCarPlayer;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

import java.util.logging.Level;

import platformgame.core.PlatformGame;
import platformgame.entities.*;

public class GameState extends AbstractAppState implements ActionListener {

    protected Node rootNode = new Node("Root Node");
    protected Node guiNode = new Node("Gui Node");

    protected BitmapText fpsText;
    protected BitmapText menuText;
    protected BitmapFont guiFont;

    // protected FlyByCamera flyCam;
    // protected ChaseCamera chaseCam;

    private Sun sun;
    //private Sky sky;
    private Terrain terrain;
    private StartingPoint startingPoint;
    //private Terrain_node terrain_node;
    //private CarPlayer player;
    //private SimpleCarPlayer player;
    private CharacterPlayer player;
    private SimpleEnemy simpleEnemy;

    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;
    
    private PlatformGame game = null;
    
    public GameState(PlatformGame game) {
    	this.game = game;

        this.game.getLogger().log(Level.SEVERE, "GameState created.");
    }
    
    public void startNewGame()  {
        System.out.println("startNewGame");
    }


    public void onAction(String name, boolean value, float tpf) {
            System.out.println("Name: " + name);
            System.out.println("Value: " + value);

            if (name.equals("CARGAME_Exit")){
                game.stop();
            }else if (name.equals("CARGAME_LoadMenu")){
                game.loadMenu();
            }

            //player.resetControls();

            if (name.equals("Lefts")) {
                if (value)
                    { player.left(true);}
                else
                    { player.left(false);}
            } else if (name.equals("Rights")) {
                if (value)
                    { player.right(true);}
                else
                    { player.right(false);}
            } else if (name.equals("Ups")) {
                if (value)
                    { player.up(true);}
                else
                    { player.up(false);}
            } else if (name.equals("Downs")) {
                if (value)
                    { player.down(true);}
                else
                    { player.down(false);}
            } else if (name.equals("Jumps")) {
                if (value)
                    { player.jump(true);}
                else
                    { player.jump(false);}
            }

            /*
             * For Car
            if (name.equals("Lefts")) {
                if (value)
                    { player.steer(.1f);}
                else
                    { player.steer(-.1f);}
            } else if (name.equals("Rights")) {
                if (value)
                    { player.steer(-.1f);}
                else
                    { player.steer(.1f);}
            } else if (name.equals("Ups")) {
                if (value)
                    { player.accelerate(-800);}
                else
                    { player.accelerate(800);}
            } else if (name.equals("Downs")) {
                if (value)
                    { player.brake(800f);}
                else
                    { player.brake(-0f);}
            } else if (name.equals("Jumps")) {
                //player.getNode().jump();
            }
             */
    }

    private void setupKeys() {
        game.getInputManager().addMapping("Lefts",  new KeyTrigger(KeyInput.KEY_LEFT));
        game.getInputManager().addMapping("Rights", new KeyTrigger(KeyInput.KEY_RIGHT));
        game.getInputManager().addMapping("Ups",    new KeyTrigger(KeyInput.KEY_UP));
        game.getInputManager().addMapping("Downs",  new KeyTrigger(KeyInput.KEY_DOWN));
        game.getInputManager().addMapping("Jumps",  new KeyTrigger(KeyInput.KEY_SPACE));
        game.getInputManager().addListener(this, "Lefts");
        game.getInputManager().addListener(this, "Rights");
        game.getInputManager().addListener(this, "Ups");
        game.getInputManager().addListener(this, "Downs");
        game.getInputManager().addListener(this, "Jumps");
    }

    public void loadText(){
        guiFont = game.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        fpsText = new BitmapText(guiFont, false);
        fpsText.setSize(guiFont.getCharSet().getRenderedSize());
        fpsText.setLocalTranslation(0, fpsText.getLineHeight(), 0);
        //fpsText.setText("Frames per second");
        guiNode.attachChild(fpsText);
        
        menuText = new BitmapText(guiFont, false);
        menuText.setSize(guiFont.getCharSet().getRenderedSize());
        menuText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f), 0);
        menuText.setText("Press [M] to go back to the Menu");
        guiNode.attachChild(menuText);
        
    }

    public Spatial getPlayer() {
        return (Spatial)player.getNode();
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        // enable depth test and back-face culling for performance
        app.getRenderer().applyRenderState(RenderState.DEFAULT);

        guiNode.setQueueBucket(Bucket.Gui);
        guiNode.setCullHint(CullHint.Never);

        game.getPhysicsSpace().enableDebug(game.getAssetManager());
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        int fps = (int) game.getTimer().getFrameRate();
        fpsText.setText("Frames per second: "+fps);

        // Replace flyCam with ChaseCamera (see example TestChaseCamera.jave) !!!!!!!!!!!!!!!!!!!1

        /*
        Vector3f camDir = flyCam.getDirection().clone().multLocal(0.6f);
        Vector3f camLeft = flyCam.getLeft().clone().multLocal(0.4f);
        flyCam.setLocation(player.getNode().getLocalTranslation());
         * Some methods don't work. Find another way for this or ask tutorial writer for correction.
         */

        // simple update and root node
        player.update(tpf);
        simpleEnemy.update(tpf);

        rootNode.updateLogicalState(tpf);
        guiNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        guiNode.updateGeometricState();

        // Place the camera behind the player
        Vector3f direction = player.getNode().getLocalRotation().getRotationColumn(2);
        Vector3f direction2 = player.getNode().getLocalRotation().getRotationColumn(1);

        float yDirection = direction2.y;
        float xDirection = direction.x;
        float zDirection = direction.z;

        Vector3f camLocation = new Vector3f(player.getNode().getWorldTranslation().x+(xDirection*10), player.getNode().getWorldTranslation().y+4f, player.getNode().getWorldTranslation().z + (zDirection*10));

        game.getCamera().setLocation(camLocation);
        game.getCamera().lookAt(player.getNode().getWorldTranslation(), Vector3f.UNIT_Y);
    }
    
    
    @Override
    public void stateAttached(AppStateManager stateManager) {
        // Load game

        loadText();

        if (game.getInputManager() != null){
            /*
            flyCam = new FlyByCamera(game.getCamera());
            flyCam.setMoveSpeed(5f);
            flyCam.registerWithInput(game.getInputManager());
             * Replaced with ChaseCam
             */
            //chaseCam = new ChaseCamera(game.getCamera(), player.getNode(), game.getInputManager());

            game.getInputManager().addMapping("CARGAME_Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
            game.getInputManager().addMapping("CARGAME_LoadMenu", new KeyTrigger(KeyInput.KEY_M));
        }

        setupKeys();

        // Add a simple Box
        /*
        Box boxshape1 = new Box(new Vector3f(-3f,1.1f,0f), 1f,1f,1f);
        Geometry cube = new Geometry("My Textured Box", boxshape1);
        Material mat_stl = new Material(game.getAssetManager(), "Common/MatDefs/Misc/SimpleTextured.j3md");
        //Texture tex_ml = game.getAssetManager().loadTexture("Interface/Logo/Monkey.jpg");
        //mat_stl.setTexture("m_ColorMap", tex_ml);
        cube.setMaterial(mat_stl);
        rootNode.attachChild(cube);
         *
         */

        sun = new Sun(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        //sky = new Sky(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        terrain = new Terrain(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        startingPoint = new StartingPoint(game.getAssetManager(), rootNode, game.getPhysicsSpace(), game.getCamera());
        //terrain_node = new Terrain_node(game.getCamera(), game.getAssetManager(), rootNode, game.getPhysicsSpace());
        //player = new CarPlayer(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        //player = new SimpleCarPlayer(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        player = new CharacterPlayer(game.getAssetManager(), rootNode, game.getPhysicsSpace(), game.getCamera());
        simpleEnemy = new SimpleEnemy(player, game.getAssetManager(), rootNode, game.getPhysicsSpace());

        /*
        if (game.getInputManager() != null){
            chaseCam = new ChaseCamera(game.getCamera(), player.getNode(), game.getInputManager());
        }
         *
         */

        game.getInputManager().addListener(this, "CARGAME_Exit",
                "CARGAME_LoadMenu");
        
        // if(flyCam != null) flyCam.setEnabled(true);
        // if(chaseCam != null) chaseCam.setEnabled(true);
    	
        game.getViewPort().attachScene(rootNode);
        game.getGUIViewPort().attachScene(guiNode);

        this.game.getLogger().log(Level.SEVERE, "GameState attached.");
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        // Unload game
        rootNode.detachAllChildren();
        guiNode.detachAllChildren();

    	game.getInputManager().removeListener(this);
        // if(flyCam != null) flyCam.setEnabled(false);
        // if(chaseCam != null) chaseCam.setEnabled(false);
    	
        game.getViewPort().detachScene(rootNode);
        game.getGUIViewPort().detachScene(guiNode);

        this.game.getLogger().log(Level.SEVERE, "GameState detached.");
    }

    @Override
    public void render(RenderManager rm) {
    }
}