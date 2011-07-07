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

package platformgame.entities;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
//import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.ArrayList;
import java.util.List;
import jme3tools.converters.ImageToAwt;

/**
 *
 * @author Vortex
 */
public class Level extends Entity {
    //terrain
    TerrainQuad terrain;
    Node terrainPhysicsNode;
    //Materials
    Material matRock;
    Material matWire;

    public Level(Camera camera, AssetManager assetManager, Node parent, PhysicsSpace physicsSpace) {
        super(assetManager, parent, physicsSpace);

        matRock = new Material(assetManager, "MatDefs/Terrain/Terrain.j3md");
        matRock.setTexture("m_Alpha", assetManager.loadTexture("Textures/Terrain/splat/alphamap.png"));
        Texture heightMapImage = assetManager.loadTexture("Textures/Terrain/splat/mountains512.png");
        Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
        grass.setWrap(WrapMode.Repeat);
        matRock.setTexture("m_Tex1", grass);
        matRock.setFloat("m_Tex1Scale", 64f);
        Texture dirt = assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(WrapMode.Repeat);
        matRock.setTexture("m_Tex2", dirt);
        matRock.setFloat("m_Tex2Scale", 32f);
        Texture rock = assetManager.loadTexture("Textures/Terrain/splat/road.jpg");
        rock.setWrap(WrapMode.Repeat);
        matRock.setTexture("m_Tex3", rock);
        matRock.setFloat("m_Tex3Scale", 128f);
        matWire = new Material(assetManager, "MatDefs/Misc/WireColor.j3md");
        matWire.setColor("m_Color", ColorRGBA.Green);

        AbstractHeightMap heightmap = null;
        try {
            heightmap = new ImageBasedHeightMap(ImageToAwt.convert(heightMapImage.getImage(), false, true, 0), 0.25f);
            heightmap.load();

        } catch (Exception e) {
            e.printStackTrace();
        }

        terrain = new TerrainQuad("terrain", 65, 513, heightmap.getHeightMap());
        terrain.setLocalTranslation(0, -30, 20);
        terrain.updateGeometricState();
        List<Camera> cameras = new ArrayList<Camera>();
        cameras.add(camera);
        TerrainLodControl control = new TerrainLodControl(terrain, cameras);
        terrain.addControl(control);
        terrain.setMaterial(matRock);
        terrain.setModelBound(new BoundingBox());
        terrain.updateModelBound();
        terrain.setLocalScale(new Vector3f(2, 2, 2));

        //TerrainPhysicsShapeFactory factory = new TerrainPhysicsShapeFactory();
        //terrainPhysicsNode = factory.createPhysicsMesh(terrain);
        terrainPhysicsNode.attachChild(terrain);
        getParent().attachChild(terrainPhysicsNode);
        getPhysicsSpace().add(terrainPhysicsNode);
    }

}
