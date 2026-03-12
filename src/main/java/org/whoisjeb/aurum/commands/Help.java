package org.whoisjeb.aurum.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.whoisjeb.aurum.Aurum;
import java.util.ArrayList;
import java.util.Map;

public class Help extends AuricCommand {
    private final Aurum plugin;

    public Help(Aurum plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Initialize menu and list of commands
        ArrayList<String> menu = new ArrayList<>();
        ArrayList<Command> commands = new ArrayList<>();

        //Fill the command list with those to which the sender has access
        for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
            PluginDescriptionFile pdf = p.getDescription();
            //noinspection unchecked
            for (String name : ((Map<String, Map<String, Object>>) pdf.getCommands()).keySet()) {
                Command cmd = Bukkit.getPluginCommand(name);
                String perm = cmd.getPermission();
                if (perm != null && sender.hasPermission(cmd.getPermission())) commands.add(cmd);
            }
        }

        //Determine target page and page count; Correct target if it's too high
        int page = (args.length >= 1) ? Integer.parseInt(args[0]) : 1;
        int pageCount = (commands.size() / 10) + 1;
        if (page < 1 || page > pageCount) page = 1;

        //Construct menu
        menu.add(message(command, "header")
                .replace("{page}", String.valueOf(page))
                .replace("{pageCount}", String.valueOf(pageCount)));
        int i = 1;
        for (Command cmd : commands) {
            //Only print the correct range of entries
            if ((i - 1) >= ((page * 10) - 10) && (i - 1) < (page * 10)) {
                menu.add(message(command, "line")
                        .replace("{command}", cmd.getName())
                        .replace("{description}", cmd.getDescription()));
            }
            i++;
        }
        sendMessages(sender, menu);
        return true;
    }
}
