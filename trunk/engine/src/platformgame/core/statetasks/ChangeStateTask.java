/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package platformgame.core.statetasks;

/**
 *
 * @author Vortex
 */

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.ViewPort;
import java.util.concurrent.Callable;

public class ChangeStateTask implements Callable
{
    private AbstractAppState oldState;
    private AbstractAppState newState;
    private ViewPort viewPort;
    private AppStateManager stateManager;

    public ChangeStateTask(AbstractAppState oldState, AbstractAppState newState, ViewPort view, AppStateManager sm)
    {
        this.oldState=oldState;
        this.newState=newState;
        this.viewPort=view;
        this.stateManager=sm;
    }

    public Object call() throws Exception
    {
        System.out.println("ChangeStateTask called");

        //viewPort.detachScene(oldState.getRootNode());
        stateManager.detach(oldState);

        //viewPort.attachScene(newState.getRootNode());
        stateManager.attach(newState);

        return null;
    }
}