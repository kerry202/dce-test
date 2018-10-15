package cn.dagongniu.oax.vpn.core;

import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import cn.dagongniu.oax.vpn.tunnel.Config;
import cn.dagongniu.oax.vpn.tunnel.RawTunnel;
import cn.dagongniu.oax.vpn.tunnel.Tunnel;
import cn.dagongniu.oax.vpn.tunnel.httpconnect.HttpConnectConfig;
import cn.dagongniu.oax.vpn.tunnel.httpconnect.HttpConnectTunnel;
import cn.dagongniu.oax.vpn.tunnel.shadowsocks.ShadowsocksConfig;
import cn.dagongniu.oax.vpn.tunnel.shadowsocks.ShadowsocksTunnel;

public class TunnelFactory {

    public static Tunnel wrap(SocketChannel channel, Selector selector) {
        return new RawTunnel(channel, selector);
    }

    public static Tunnel createTunnelByConfig(InetSocketAddress destAddress, Selector selector) throws Exception {
        if (destAddress.isUnresolved()) {
            Config config = ProxyConfig.Instance.getDefaultTunnelConfig(destAddress);
            if (config instanceof HttpConnectConfig) {
                return new HttpConnectTunnel((HttpConnectConfig) config, selector);
            } else if (config instanceof ShadowsocksConfig) {
                return new ShadowsocksTunnel((ShadowsocksConfig) config, selector);
            }
            throw new Exception("The config is unknow.");
        } else {
            return new RawTunnel(destAddress, selector);
        }
    }

}
