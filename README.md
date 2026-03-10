![Banner.png](assets/Banner.png)
### A project-poseidon plugin designed as an alternative to Essentials and ZCore.

---

### Commands:
| Command                                           | Main Aliases  | Description                                 | Permission              | Default |
|---------------------------------------------------|---------------|---------------------------------------------|-------------------------|---------|
| **/aurum** [reload/info]                          | /au           | Display plugin's info, or reloads its data. | `aurum.aurum`           | Yes     |
| **/balance** [player]                             | /bal          | Prints the sender's or another's balance.   | `aurum.balance`         | No      |
| **/balancetop** [page]                            | /baltop       | Displays a page of the richest players.     | `aurum.balance`         | No      |
| **/ban** \<player> [reason]                       |               | Bans the specified user.                    | `aurum.ban`             | No      |
| **/banlist** [page]                               |               | Prints a page of currently active bans.     | `aurum.ban`             | No      |
| **/banlog** [page]                                |               | Prints a page of past bans, active or not.  | `aurum.ban`             | No      |
| **/delhome** \<home>                              | /delh         | Deletes the specified home.                 | `aurum.home`            | No      |
| **/delwarp** \<warp>                              |               | Deletes the specified warp.                 | `aurum.delwarp`         | No      |
| **/discord**                                      |               | Links to the server's discord.              | `aurum.discord`         | No      |
| **/economy** \<give/take/set> \<player> \<amount> | /eco, /econ   | Allows for managing the server economy.     | `aurum.economy`         | No      |
| **/grounditemclear**                              | /gic, /iclear | Clears all ground items.                    | `aurum.grounditemclear` | No      |
| **/heal** [player]                                |               | Heals the sender or a target.               | `aurum.heal`            | No      |
| **/help** [page]                                  |               | Displays available commands.                | `aurum.help`            | No      |
| **/home** \<home>                                 | /h            | Warps the sender to the specified home.     | `aurum.home(.other)`    | No      |
| **/homes** [player]                               |               | Lists the sender's or target's homes.       | `aurum.home(.other)`    | No      |
| **/item** \<item> \<quantity>                     | /i            | Gives the sender or target an item.         | `aurum.item`            | No      |
| **/killall** [all/passive/hostile]                | /ka, /purge   | Kills hostile and/or passive mobs.          | `aurum.killall`         | No      |
| **/modview** [player]                             | /modv         | Displays info on a player.                  | `aurum.modview`         | No      |
| **/motd**                                         |               | Prints the server's MOTD.                   | `aurum.motd`            | Yes     |
| **/mute** \<player>                               |               | Mutes the target.                           | `aurum.mute`            | No      |
| **/nickname** \<nickname/clear> [player]          | /nick         | Changes the sender's or another's nickname. | `aurum.nickname`        | No      |
| **/offlineteleport** \<player>                    | /otp          | Teleports the sender to an offline player.  | `aurum.teleport`        | No      |
| **/pay** \<player> \<amount>                      |               | Sends the target an amount of money.        | `aurum.pay`             | No      |
| **/playerlist** [page]                            | /list, /who   | Lists the currently online players.         | `aurum.playerlist`      | Yes     |
| **/rules**                                        |               | Displays the server rules.                  | `aurum.rules`           | Yes     |
| **/sethome** \<home>                              | /seth         | Sets a new home at the sender's position.   | `aurum.home`            | No      |
| **/setspawn**                                     |               | Sets spawn at the sender's position.        | `aurum.setspawn`        | No      |
| **/setwarp** \<warp>                              |               | Sets a new warp at the sender's position.   | `aurum.setwarp`         | No      |
| **/spawn**                                        |               | Warps the sender to spawn.                  | `aurum.spawn`           | No      |
| **/sudo** \<victim> \<cmd/c:msg>                  |               | Forces one to chat or run a command.        | `aurum.sudo`            | No      |
| **/teleport** \<player/x, y, z>                   | /tp           | TP's the sender to a player or position.    | `aurum.teleport`        | No      |
| **/teleportaccept** [player]                      | /tpaccept     | Accepts a teleport request.                 | `aurum.teleportask`     | No      |
| **/teleportask** \<player>                        | /tpa          | Requests that the sender tp to the target.  | `aurum.teleportask`     | No      |
| **/teleportaskhere** \<player>                    | /tpahere      | Requests that the target tp to the sender.  | `aurum.teleportask`     | No      |
| **/teleportdeny** [player]                        | /tpdeny       | Denies a teleport request.                  | `aurum.teleportask`     | No      |
| **/tellraw** \<message>                           | /tr           | Prints what is typed after /tellraw.        | `aurum.tellraw`         | No      |
| **/time** \day/dawn/dusk/night>                   | /settime      | Changes the time to the specified time.     | `aurum.time`            | No      |
| **/unban** \<player>                              |               | Unbans the target.                          | `aurum.ban`             | No      |
| **/unmute** \<player>                             |               | Unmutes the target.                         | `aurum.mute`            | No      |
| **/unwarn** \<player>                             |               | Unwarns the target.                         | `aurum.warn`            | No      |
| **/warn** \<player> [reason]                      |               | Warns the target.                           | `aurum.warn`            | No      |
| **/warp** \<warp>                                 |               | TP's the sender to the specified warp.      | `aurum.warp`            | No      |
| **/warps**                                        |               | Lists the server's warps.                   | `aurum.warp`            | No      |
| **/weather** [clear/rain]                         | /setweather   | Changes the weather.                        | `aurum.weather`         | No      |
| **/whois** \<nickname>                            | /realname     | Prints the username of an nick'd player.    | `aurum.whois`           | Yes     |

### API
Other plugins can access Aurum's configuration, punishment data, and userdata via `Aurum.api()`.