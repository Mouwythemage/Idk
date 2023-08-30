// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.client.ui.copenheimer.servers;

import com.google.common.collect.Lists;
import net.minecraft.class_642;
import java.util.List;

public class ServerList
{
    private final List<class_642> servers;
    
    public ServerList() {
        this.servers = (List<class_642>)Lists.newArrayList();
    }
    
    public class_642 get(final int index) {
        return this.servers.get(index);
    }
    
    public void remove(final class_642 serverInfo) {
        this.servers.remove(serverInfo);
    }
    
    public void add(final class_642 serverInfo) {
        this.servers.add(serverInfo);
    }
    
    public int size() {
        return this.servers.size();
    }
    
    public void set(final int index, final class_642 serverInfo) {
        this.servers.set(index, serverInfo);
    }
}
