# Aurum
### A basic Essentials alternative for [project poseidon](https://github.com/retromcorg/Project-Poseidon).
> [!IMPORTANT]
> The plugin is still very much in development. All planned features are listed, but only the checked off are available.
## Features Overview
### Core Features:
- [x] [Custom Server Spawn](#spawn)
- [x] [Server Warps](#warps)
- [x] [Player Homes](#homes)
- [x] [Nicknames](#nicknames)
- [x] [Listing online players](#listing-online-players)
- [x] [Teleportation](#teleportation)
- [ ] Economy
### Moderation Tools:
- [x] [Time and Weather control](#time-and-weather-control)
- [x] [Item Giving](#item-giving)
- [ ] Godmode
- [ ] [Heal](#healing)
- [ ] Vanish
- [ ] Invsee
- [x] [Warnings](#warnings)
- [x] [Modview](#modview)
### Misc. Features:
- [x] [MOTD](#motd)
- [x] [/rules](#rules)
- [x] [/discord](#discord)
- [x] [/tellraw](#tellraw)
- [x] [/sudo](#sudo)
- [x] [API](#api)

> [!IMPORTANT]
> Remember to grant [permissions](#permissions) for any commands you want players to be able to use.

## Spawn
Authorized players can define a custom spawnpoint for the server using `/setspawn`.  
Running `/spawn` will warp the sender to said defined spawn, stored in the plugin config.

## Warps
Players can teleport to a specified server warp using `/warp`.  
Players can view a list of the server's warps with `/warps`.  
If `per-warp-perms` is true in the config, a seperate permission is required for every warp.

## Homes
Players can define a personal warp at their position using `/sethome`.  
Using `/home` teleports one to a home, and `/delhome` deletes one.

If a player is allowed to set more than 1 home, the above commands must be provided the home's name.  
Players can see a list of their homes and how many more they can set using `/homes`.

## Nicknames
Players who are given permission to can assign themselves custom display names using `/nickname set <nickname>`.  
Using `/nickname clear` clears one's nickname.  
Authorized players can also add another *online* player's username to end of the commands to set and clear others' nicknames.  
A nickname will only be able to use colors if the player is permitted to use colors in chat.

Optionally, to mark nicknames, a configurable string can be set to prepend all nicknames.

## Listing Online Players
A list of online players can be viewed via `/playerlist`, `/list`, or `/who`.  
It also displays the amount of players online and the player cap.

## Teleportation
Players who are permitted to can use `/tp <player|position>` to teleport to another online player, or to a
specified position (x y z).  
Additionally, authorized players can teleport to where an offline player last was using `/otp`.

Under a different permission, players can request to teleport to other online players using `/tpa`.  
The player to whom a request is sent must consent to the teleportation using `/tpaccept` for it to occur.
They can deny a request with `/tpdeny`.  
If there are multiple requests to one person, one can accept/deny a specific request by adding the name of the requester
in the aforementioned commands.  
Teleport requests expire after 1 minute.

## Time and Weather Control
Authorized players can change the server time using `/time <day|night|dawn|dusk>`.  
The same can be done with the weather using `/weather <clear|rain>`.

## Item Giving
The command `/item` or `/i` can be used to give the sender or an online player items.

## Healing
The command `/heal` restores the hp of a specified player, or the user, to 10 hearts (20 HP).

## Warnings
Warnings, given out with `/warn <player> [reason]`, are a way of keeping track of how many times a player was warned, and why;  
Serving to aid moderation teams with making sure players get due warning.

A warning can be taken back with `/unwarn <player> <reason>`.

## Modview
Modview is a command that displays useful information about a player regarding server moderation.
* UUID
* Last used IP
* Ban status/reason
* First join and last seen dates

## Miscellaneous Features
### MOTD
The MOTD (Message of the day) is a fully customiazable message sent to players upon joining the server.  
It is defined in the plugin config, and can be any number of lines.
### Rules
A list of a server's rules can be defined in the config to be printed upon usage of `/rules`.  
It is reccomeneded that they are numbered and consice.
### Discord
If a server has an associated discord, its link can be configured to be printed upon using `/discord`.  
It will not be clickable.
### Tellraw
The command `/tellraw` will broadcast whatever is put after it, allowing authorized players to send theoretically any message.
### Sudo
Players can be forced to run any command they are allowed to with `/sudo`.  
They cna also be forced to chat any message if it is prepended by `c:`.

## API
Other plugins can interact with the properties in the Aurum userdata files, including making new custom properties.  
For example, a statistics plugin can add properties such as `stats.blocks-mined` instead of it having to handle
userdata storage itself.

The API can be accessed by using `Aurum.api()...` when importing `org.whoisjeb.aurum.AurumAPI` and
`org.whoisjeb.aurum.Aurum` in a java class.

## Permissions
- `aurum.player`: Grants access to the plugin's basic player commands.
  - `aurum.help`: Grants access to /help.
  - `aurum.spawn`: Grants access to /spawn.
  - `aurum.rules`: Grants access to /rules.
  - `aurum.discord`: Grants access to /discord.
  - `aurum.playerlist`: Grants access to /playerlist / /who / /list.
  - `aurum.home`: Grants access to /home, /sethome, /delhome, and /homes.
  - `aurum.warp`: Grants access to /warp and /warps.
  - `aurum.teleportask`: Grants access to /tpa, /tpahere, /tpaccept, and /tpdeny
- `aurum.ban`: Grants the ability to /ban and /unban players.
- `aurum.maxhomes.n`: Grants the ability to set n homes. Default is 1.
- `aurum.warp.xyz`: Grants access to /warp xyz, if per-warp-perms are enabled.
- `aurum.setwarp`: Grants access to /setwarp.
- `aurum.delwarp`: Grants access to /delwarp.
- `aurum.color`: Grants the ability to use colors in chat and on signs.
- `aurum.nickname`: Grants access to /nickname / /nick.
- `aurum.nickname.others`: Allows /nickname to modify others' nicknames.
- `aurum.whois`: Grants access to /whois.
- `aurum.time`: Grants access to /time.
- `aurum.weather`: Grants access to /weather.
- `aurum.teleport`: Grants access to /tp and /tphere.
- `aurum.item`: Grants access to /item / /i.
- `aurum.tellraw`: Grants access to /tellraw.
- `aurum.sudo`: Grants access to /sudo.
- `aurum.heal`: Grants access to /heal.
- `aurum.modview`: Grants access to /modview.
- `aurum.warn`: Grants access to /warn and /unwarn.