TunnelliJ plugin for IntelliJ IDEA
==================================

    Introduction
        TunnelliJ is the plugin for IntelliJ IDEA.
        Tunnelli is a tunnel tool. It listens on the specified port
        and forwards all incoming traffic to specified destination.
        User can look at the traffic. It is useful when debugging
        network applications, such Web applications, Web Services
        etc.

    Version
        TunnelliJ $TUNNELLIJ_VERSION$

    Author
        Milan Boruvka

    Email
        milan.boruvka@gmail.com

    WWW
        http://www.boruvka.net
        http://www.intellij.org/twiki/bin/view/Main/TunnelliJPlugin

    Prerequisities
        IntelliJ IDEA 3.0.x or higher
        To download Intellij IDEA go to http://www.intellij.com.

    Tested on
        IntelliJ IDEA 3.0.5
        IntelliJ IDEA 4.0 RC (build #1122)
        IntelliJ IDEA 7.0 RC1

    Known issues:
        After changing port and host values and pressing
        start, there is a "assertion failed" error message
        on IntelliJ Idea 4.0 RC (build #1122), you can
        ignore it.

    Bug Reports
        Any bugs, questions or suggestions please send to
        milan.boruvka@gmail.com.

    Installation
    	You can (un)install this plugin from within IDE: 
    	Settings -> Plugins dialog.

    Manual Installation
        Remove previous version of this plugin, if present.
        Copy JAR file into idea/plugins/ directory and restart IDEA,
        open some project.
        You should see TunnelliJ tab at the bottom of the IDEA window.
        You can assign a hot key for TunnelliJ window - use menu
        Options -> Keymaps.

    Usage

        Example:
           I want to sniff and look at calls between client I develop
           and the Web Service running at http://tomcat:8080/service.


           Start TunnelliJ with these parameters:

                4444 as port where it listens
                tomcat as destination hostname
                8080 as destination port

           Force your client to open connections to localhost:4444
           (not directly to tomcat:8080). E.g. change the constant
           used in your code when opening connection and recompile
           the class).

               Alternatively you can force your client to use port
               4444 by setting HTTP proxy parameters to your JVM:

                -Dhttp.proxyHost=localhost -Dhttp.proxyPort=4444

                In this case JVM will try to redirect all HTTP connections
                at port 4444, thinking there is a proxy. Tunnel will
                forward all incoming traffic to specifed host and port.

           Run your client. You should see all calls as lines
           in the stored calls list, when you open TunnelliJ
           window.

           One line in the list is one connection opened during
           communication. One connection can be used for more than one call,
           for example when server allows HTTP option KeepConnectionAlive.

           All outgoing traffic (requests) is in the left side text area,
           all incoming (replies) is in the right.

           You can edit the text areas to be more readable from them,
           use Ctrl+C and to copy the content.

           You also may find useful "Wrap/Unwrap" button which wraps/unwraps
           the contect of text areas to be more readable.

history
-------

 0.9 beta  - basic version
 0.9.1     - bug fixed (socket already used)
           - first 30 chars from output message shown in list
 1.0       - internal release :-)
 1.1       - internal release :-)
 1.2       - compatible with idea 4.0RC, new icons (because the
             missing icons dissallowed to run it under Auroras..),
             storing properties into file
