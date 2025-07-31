package com.startToStop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StartToStop extends JavaPlugin {

    @Override
    public void onEnable() {
        // 插件加载
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            getLogger().info("服务器已完全启动，开始关闭倒计时...");
            startCountdown();
        }, 20L);
    }

    private void startCountdown() {
        int[] warningTimes = {10, 5, 3, 2, 1};
        int maxTime = warningTimes[0];

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            int remaining = maxTime;

            @Override
            public void run() {
                // 检查是否是预设的提示时间点
                for (int time : warningTimes) {
                    if (remaining == time) {
                        getLogger().warning("服务器将在 " + time + " 秒后关闭！");
                        break;
                    }
                }

                if (remaining <= 0) {
                    getLogger().info("正在安全关闭服务器...");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
                    Bukkit.getScheduler().cancelTasks(StartToStop.this);
                }

                remaining--;
            }
        }, 0L, 20L);
    }

    @Override
    public void onDisable() {
        // 插件卸载
        getLogger().info("自动关服插件已卸载，欢迎下次使用awa");
    }
}
