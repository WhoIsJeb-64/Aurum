# Aurum
### A basic Essentials alternative for [project poseidon](https://github.com/retromcorg/Project-Poseidon).
> [!IMPORTANT]
> The plugin is still very much in development. All planned features are listed, but only the checked off are available.
## Features Overview
### Core Features:
- [ ] [Custom Server Spawn](#spawn)
- [ ] Server Warps
- [ ] Player Homes
- [ ] Nicknames
- [ ] Economy
- [ ] TP Requests
- [ ] Configurable Sleep %
- [ ] Listing online players
### Moderation Tools:
- [ ] Godmode, Heal, Vanish, Invsee
- [ ] Warnings
- [ ] Punishment & Ban Logs
- [ ] Modview
### Misc. Features:
- [x] [MOTD](#motd)
- [x] [/rules](#rules)
- [x] [/discord](#discord)
- [ ] Teleport safety validation

> [!IMPORTANT]
> Remember to grant permissions for any commands you want players to be able to use.

## Spawn
Authorized players can define a custom spawnpoint for the server using `/setspawn`.  
Running `/spawn` will warp the sender to said defined spawn, stored in the plugin config.

## Miscellaneous Features
### MOTD
The MOTD (Messages of the day) is a fully customiazable message sent to players upon joining the server.  
It is defined in the plugin config, and can be any number of lines.
### Rules
A list of a server's rules can be defined in the config to be printed upon usage of `/rules`.  
It is reccomeneded that they are numbered and consice.
### Discord
If a server has an associated discord, its link can be configured to be printed upon using `/discord`.  
It will not be clickable.