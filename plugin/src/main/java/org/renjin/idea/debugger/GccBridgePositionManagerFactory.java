package org.renjin.idea.debugger;

import com.intellij.debugger.PositionManager;
import com.intellij.debugger.PositionManagerFactory;
import com.intellij.debugger.engine.DebugProcess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GccBridgePositionManagerFactory extends PositionManagerFactory {


  @Nullable
  @Override
  public PositionManager createPositionManager(@NotNull DebugProcess debugProcess) {
    return new GccBridgePositionManager(debugProcess);
  }
}
