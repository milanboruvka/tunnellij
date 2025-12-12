# TunnelliJ

A TCP tunnel and network traffic sniffer plugin for JetBrains IntelliJ IDEA.

_Note: I wrote this plugin in 2004 and I am quite amazed that after small revamp (thanks Claude Code) it still works fine!_

## Description

TunnelliJ allows you to monitor and analyze network traffic between client and server applications directly within your IDE. It's useful for debugging web applications, web services, and any TCP-based communication.

## Features

- Listen on a configurable local port (default: 4444)
- Forward all traffic to a destination host:port (default: localhost:6060)
- Capture and display both request and response data
- View connection timestamps, data sizes, and duration
- Line wrapping toggle for better readability
- Settings persistence between sessions

## Installation

1. Download `tunnellij_1.6.jar` from the releases or build from source
2. In IntelliJ IDEA: **Settings → Plugins → ⚙️ → Install Plugin from Disk...**
3. Select the JAR file and restart the IDE

## Usage

1. Open the TunnelliJ tool window (bottom panel)
2. Configure source port, destination host, and destination port
3. Click **Start** to begin capturing traffic
4. Point your client application to the configured source port
5. View captured connections in the list and inspect request/response data

## Building from Source

Requires Apache Ant and JDK 17+.

```bash
ant jar
```

The plugin JAR will be created in the `build/` directory.

## Author

**Milan Boruvka**
- Email: milan.boruvka@gmail.com
- Website: http://www.boruvka.net

## License

Apache License 2.0

## Links

- [JetBrains Plugin Repository](http://plugins.jetbrains.com/plugin/155?pr=idea)
