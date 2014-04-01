package org.unbiquitous.uImpala.engine.io;

import java.util.HashSet;
import java.util.Iterator;

import org.unbiquitous.uImpala.engine.core.GameComponents;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;

/**
 * Class for keyboards management.
 * @author Pimenta
 *
 */
public final class KeyboardManager implements InputManager {
  public KeyboardManager() {
    gateway = GameComponents.get(Gateway.class);
  }
  
  public IOResource alloc() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public boolean free(IOResource rsc) {
    // TODO Auto-generated method stub
    return false;
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  public void update() {
    //TODO
    gateway.hashCode();//FIXME remove
    for (KeyboardSource ks : screenKeyboards)
      ks.update();
  }
  
  public void close() {
    //TODO
    for (Iterator<KeyboardSource> i = screenKeyboards.iterator(); i.hasNext();) {
      i.next().close();
      i.remove();
    }
  }
  
  /**
   * Engine's private use.
   */
  protected HashSet<KeyboardSource> screenKeyboards = new HashSet<KeyboardSource>();
  
  private Gateway gateway;
}