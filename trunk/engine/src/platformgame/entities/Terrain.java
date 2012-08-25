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
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
//import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Vortex
 */
public class Terrain extends Entity {
    public Terrain(AssetManager assetManager, Node parent, PhysicsSpace physicsSpace) {
        super(assetManager, parent, physicsSpace);

        //com.jme3.terrain
        //BufferGeomap

        Node terrain = (Node) assetManager.loadModel("Scenes/terrain_1.j3o");

        terrain.setLocalTranslation(new Vector3f(0,-1,10));
        terrain.updateGeometricState();

        //Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/WireColor.j3md");
        //Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat3.setColor("m_Color", ColorRGBA.Red);
        //terrain.attachDebugShape(mat3);


        getParent().attachChild(terrain);
        getPhysicsSpace().addAll(terrain);
    }

}
