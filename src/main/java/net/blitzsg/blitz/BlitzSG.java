package net.blitzsg.blitz;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import net.blitzsg.blitz.command.blood.BloodCommand;
import net.blitzsg.blitz.command.broadcast.BroadcastCommand;
import net.blitzsg.blitz.command.coins.SetCoinsCommand;
import net.blitzsg.blitz.command.hub.KaboomCommand;
import net.blitzsg.blitz.command.join.JoinCommand;
import net.blitzsg.blitz.command.list.ListCommand;
import net.blitzsg.blitz.command.party.PartyChatCommand;
import net.blitzsg.blitz.command.party.PartyCommand;
import net.blitzsg.blitz.command.taunt.TauntCommand;
import net.blitzsg.blitz.command.vote.VoteCommand;
import net.blitzsg.blitz.party.PartyManager;
import net.blitzsg.blitz.player.IPlayerHandler;
import net.blitzsg.blitz.player.IPlayerManager;
import net.blitzsg.blitz.command.fireworks.FireworkCommand;
import net.blitzsg.blitz.command.hub.HubCommand;
import net.blitzsg.blitz.command.message.MessageCommand;
import net.blitzsg.blitz.command.message.ReplyCommand;
import net.blitzsg.blitz.command.rank.RankCommand;
import net.blitzsg.blitz.command.world.WorldCommand;
import net.blitzsg.blitz.game.Game;
import net.blitzsg.blitz.gamestar.StarManager;
import net.blitzsg.blitz.kit.KitManager;
import net.blitzsg.blitz.map.MapManager;
import net.blitzsg.blitz.punishments.commands.ban.BanCommand;
import net.blitzsg.blitz.punishments.commands.ban.UnbanCommand;
import net.blitzsg.blitz.punishments.commands.mute.MuteCommand;
import net.blitzsg.blitz.punishments.commands.mute.UnmuteCommand;
import net.blitzsg.blitz.statistics.LeaderboardManager;
import net.blitzsg.blitz.statistics.StatisticsManager;
import net.blitzsg.blitz.util.ChatUtil;
import net.blitzsg.blitz.util.EnchantListener;
import net.blitzsg.blitz.command.game.GameCommand;
import net.blitzsg.blitz.cosmetic.CosmeticsManager;
import net.blitzsg.blitz.elo.EloManager;
import net.blitzsg.blitz.game.GameHandler;
import net.blitzsg.blitz.game.GameManager;
import net.blitzsg.blitz.game.GameMobHandler;
import net.blitzsg.blitz.punishments.PunishmentManager;
import net.blitzsg.blitz.rank.RankManager;
import net.blitzsg.blitz.scoreboard.ScoreboardManager;
import net.blitzsg.blitz.database.Database;
import net.blitzsg.blitz.menu.MenuListener;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class BlitzSG extends JavaPlugin {

    public static String CORE_NAME = EnumChatFormat.GRAY + "[" + EnumChatFormat.RED + "B-SG" + EnumChatFormat.GRAY + "]: " + EnumChatFormat.WHITE;

    public static BlitzSG instance;
    private StatisticsManager statisticsManager;
    private MapManager mapManager;
    private IPlayerManager iPlayerManager;
    private GameManager gameManager;
    private LeaderboardManager leaderboardManager;
    private ScoreboardManager scoreboardManager;
    private RankManager rankManager;
    private EloManager eloManager;
    private GameHandler gameHandler;
    private KitManager kitManager;
    private StarManager starManager;
    private PunishmentManager punishmentManager;
    private HikariDataSource hikari;
    public static Location lobbySpawn;
    private Database db;
    private CosmeticsManager cosmeticsManager;
    private PartyManager partyManager;
    private long startTime;

    public BlitzSG() {
        instance = this;
        startTime = System.currentTimeMillis();
    }

    public Database db() {
        return db;
    }


    public void onEnable() {

        //delete all directories in the /worlds folder except for the world folder


        db = new Database();
        statisticsManager = new StatisticsManager();
        iPlayerManager = new IPlayerManager();
        rankManager = new RankManager();
        kitManager = new KitManager();
        mapManager = new MapManager();
        BlitzSG.getInstance().getMapManager().deleteWorlds();

        gameManager = new GameManager();
        scoreboardManager = new ScoreboardManager();
        eloManager = new EloManager();
        starManager = new StarManager();

        punishmentManager = new PunishmentManager();
        cosmeticsManager = new CosmeticsManager();
        cosmeticsManager.init();
        leaderboardManager = new LeaderboardManager();
        partyManager = new PartyManager(iPlayerManager);


        //Register Commands::
        registerCommands();

//        getCommand("nick").setExecutor(new NicknameCommand());

        //Register Handlers:
        getServer().getPluginManager().registerEvents(this.gameHandler = new GameHandler(), this);
        getServer().getPluginManager().registerEvents(new GameMobHandler(), this);
        getServer().getPluginManager().registerEvents(new IPlayerHandler(), this);
        getServer().getPluginManager().registerEvents(new EnchantListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(scoreboardManager.getScoreboardHandler(), this);

        lobbySpawn = new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0);

        for (Player p : getServer().getOnlinePlayers()) {
            statisticsManager.loadPlayer(p.getUniqueId());
            iPlayerManager.toLobby(p);
        }
        scoreboardManager.runTaskTimer(this, 20, 20);

        //KarhuAPI.getEventRegistry().addListener(anticheat);
    }


    private void registerCommands() {
        Bukkit.getConsoleSender().sendMessage(ChatUtil.color(" "));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.color("&d&lLoading Commands..."));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.color(" "));
        new RankCommand();
        new GameCommand();
        new HubCommand();
        new FireworkCommand();
        new MessageCommand();
        new ReplyCommand();
        new WorldCommand();
        new BanCommand();
        new UnbanCommand();
        new MuteCommand();
        new UnmuteCommand();
        new SetCoinsCommand();
        new BroadcastCommand();
        new JoinCommand();
        new ListCommand();
        new TauntCommand();
        new VoteCommand();
        new PartyCommand();
        new PartyChatCommand();
        new KaboomCommand();
        new BloodCommand();
        Bukkit.getConsoleSender().sendMessage(ChatUtil.color("&d&lFinished Loading Commands!"));

    }

    public void onDisable() {
        BlitzSG.getInstance().getStatisticsManager().saveAll();
        for (Game g : gameManager.getRunningGames()) {
            g.resetGame();
        }

        BlitzSG.getInstance().getStatisticsManager().saveAll();
        BlitzSG.getInstance().getMapManager().deleteWorlds();
    }

    public static void broadcast(String message, World world) {
        if (world == null)
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
        else
            world.getPlayers().forEach(player -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
    }

    public static void send(Player p, String message) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static BlitzSG getInstance() {
        return instance;
    }


}
