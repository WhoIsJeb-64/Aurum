# Aurum
### A basic Essentials alternative for [project poseidon](https://github.com/retromcorg/Project-Poseidon).
> [!IMPORTANT]
> The plugin is still very much in development. All planned features are listed, but only the checked off are available.
## Features Overview
### Core Features:
- [x] [Custom Server Spawn](#spawn)
- [ ] Server Warps
- [x] [Player Homes](#homes)
- [ ] Nicknames
- [ ] Economy
- [ ] Teleportation
- [ ] Configurable Sleep %
- [ ] Listing online players
### Moderation Tools:
- [ ] Godmode, Heal, Vanish, Invsee
- [ ] Warnings
- [ ] Punishment & Ban Logs
- [ ] Modview
### Misc. Features:
- [ ] Time and Weather control
- [x] [MOTD](#motd)
- [x] [/rules](#rules)
- [x] [/discord](#discord)
- [ ] Teleport safety validation
- [ ] API

> [!IMPORTANT]
> Remember to grant [permissions](#permissions) for any commands you want players to be able to use.

## Spawn
Authorized players can define a custom spawnpoint for the server using `/setspawn`.  
Running `/spawn` will warp the sender to said defined spawn, stored in the plugin config.

## Homes
Players can define a personal warp at their position using `/sethome`.  
Using `/home` teleports one to a home, and `/delhome` deletes one.

If a player is allowed to set more than 1 home, the above commands must be provided the home's name.  
Players can see a list of their homes and how many more they can set using `/homes`.

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

## Permissions
- `aurum.player`: Grants access to the plugin's basic player commands.
  - `aurum.spawn`: Grants access to /spawn.
  - `aurum.rules`: Grants access to /rules.
  - `aurum.discord`: Grants access to /discord.
  - `aurum.home`: Grants access to /home, /sethome, /delhome, and /homes.
  - `aurum.maxhomes.n` Grants the ability to set n homes. Default is 1.
- `aurum.color`: Grants the ability to use colors in chat and on signs.