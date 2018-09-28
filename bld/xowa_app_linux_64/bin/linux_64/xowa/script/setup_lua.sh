#!/bin/bash
#
# This script is for post-processing setup steps.
# It is run by XOWA once per operating system; see /xowa/user/anonymous/data/cfg/user_system_cfg.gfs
# It is currently used for lua, but may be extended to other binaries
#
# ---
# Lua
# ---
# This section allows the user to grant the Execute permission to "lua" *once* and only *once*
# * The "lua" binary requires Execute permission
# * However, XOWA bundles all binaries in every xowa_app.zip file, including the "lua" binary
# * The problem arises if "xowa_app.zip" file were to include "lua" in /xowa/bin/<OS>/lua/lua
# * When extracting the zip archive, the embedded binary would overwrite the existing one and the Execute permission would be lost
# * Hence, every upgrade would force the user to re-grant the Execute permission to "lua"
#
# To avoid the perpetual re-grant, the following is done:
# * The xowa_app.zip file deploys a binary called "lua_install"
# * The script renames "lua_install" to "lua" and grants Execute to "lua"
# * Subsequent xowa upgrades will then copy down a redundant "lua_install". The existing "lua" is unaffected
#
# This is not an elegant solution, but the only expense is the following:
# * an extra 500 KB file which the user can always delete
# * this script and the accompanying documentation

# Declare variables
OS_NAME=linux_64
# $0 is passed in by bash; EX: "/xowa/bin/linux_64/xowa/script/setup_lua.sh"
SETUP_LUA_FILE=$0
# $1 is passed in by XOWA; EX: "/xowa"
XOWA_DIR=$1
LUA_INSTALL_FILE=$XOWA_DIR/bin/$OS_NAME/lua/lua_install
LUA_FILE=$XOWA_DIR/bin/$OS_NAME/lua/lua

# Rename "lua_install" to "lua"; -f will overwrite it if it exists
mv -f $LUA_INSTALL_FILE $LUA_FILE

# Grant execute permission for lua (7=All,User; 7=All,Group; 4=Read,Everyone)
chmod 774 $LUA_FILE
