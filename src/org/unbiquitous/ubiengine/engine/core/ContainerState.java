package org.unbiquitous.ubiengine.engine.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to automatically update and render game objects.
 * Extend only to implement a constructor, to add game objects.
 * @see GameObject
 * @see GameState
 * @author Pimenta
 *
 */
public abstract class ContainerState extends GameState {
  /**
   * Add a game object to the game state.
   * @param o Game object.
   */
  protected void add(GameObject o) {
    objects.add(o);
  }
  
  /**
   * Use this class to implement game objects of a ContainerState.
   * @see ContainerState
   * @author Pimenta
   *
   */
  public static abstract class GameObject {
    /**
     * Flag to tell the game state if this object must be destroyed.
     */
    protected boolean destroy = false;
    
    /**
     * Method to implement update.
     */
    protected abstract void update();
    
    /**
     * Method to implement rendering.
     */
    protected abstract void render(RendererContainer renderers);
    
    /**
     * Handle a pop from the stack of game states.
     * @param args Arguments passed from the state popped.
     */
    protected abstract void wakeup(Object... args);
    
    /**
     * Method to close whatever is necessary.
     */
    protected abstract void destroy();
    
    /**
     * Add a child game object.
     * @param o Game object.
     */
    protected void add(GameObject o) {
      objects.add(o);
    }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
    private void updateTree() {
      update();
      for (GameObject o : objects) {
        if (!o.destroy)
          o.updateTree();
      }
    }
    
    private void renderTree(RendererContainer renderers) {
      render(renderers);
      Iterator<GameObject> i = objects.iterator();
      while (i.hasNext()) {
        GameObject o = i.next();
        if (!o.destroy)
          o.renderTree(renderers);
        else {
          o.destroyTree();
          i.remove();
        }
      }
    }
    
    private void wakeupTree(Object... args) {
      wakeup(args);
      for (GameObject o : objects)
        o.wakeupTree(args);
    }
    
    private void destroyTree() {
      destroy();
      for (GameObject o : objects)
        o.destroyTree();
    }
    
    private List<GameObject> objects = new LinkedList<GameObject>();
  }
  
  /**
   * Engine's private use.
   */
  protected void update() {
    for (GameObject o : objects) {
      if (!o.destroy)
        o.updateTree();
    }
  }
  
  /**
   * Engine's private use.
   */
  protected void render() {
    RendererContainer renderers = new RendererContainer();
    Iterator<GameObject> i = objects.iterator();
    while (i.hasNext()) {
      GameObject o = i.next();
      if (!o.destroy)
        o.renderTree(renderers);
      else {
        o.destroyTree();
        i.remove();
      }
    }
    renderers.render();
  }
  
  /**
   * Engine's private use.
   */
  protected void wakeup(Object... args) {
    for (GameObject o : objects)
      o.wakeupTree(args);
  }
  
  /**
   * Engine's private use.
   */
  protected void close() {
    for (GameObject o : objects)
      o.destroyTree();
  }
  
  private List<GameObject> objects = new LinkedList<GameObject>();
}
