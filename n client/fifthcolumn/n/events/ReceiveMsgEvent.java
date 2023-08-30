// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.events;

import net.minecraft.class_2556;
import net.minecraft.class_2561;
import meteordevelopment.meteorclient.events.Cancellable;

public class ReceiveMsgEvent extends Cancellable
{
    private static final ReceiveMsgEvent INSTANCE;
    private class_2561 message;
    private String sender;
    private class_2556.class_7602 params;
    private boolean modified;
    
    public static ReceiveMsgEvent get(final class_2561 message, final String sender, final class_2556.class_7602 params) {
        ReceiveMsgEvent.INSTANCE.setCancelled(false);
        ReceiveMsgEvent.INSTANCE.message = message;
        ReceiveMsgEvent.INSTANCE.sender = sender;
        ReceiveMsgEvent.INSTANCE.params = params;
        ReceiveMsgEvent.INSTANCE.modified = false;
        return ReceiveMsgEvent.INSTANCE;
    }
    
    public class_2561 getMessage() {
        return this.message;
    }
    
    public String getSender() {
        return this.sender;
    }
    
    public class_2556.class_7602 getParams() {
        return this.params;
    }
    
    public void setMessage(final class_2561 message) {
        this.message = message;
        this.modified = true;
    }
    
    public boolean isModified() {
        return this.modified;
    }
    
    static {
        INSTANCE = new ReceiveMsgEvent();
    }
}
