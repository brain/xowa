/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.html.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.xowa.gui.*;
public class Xoh_module_itm__xoui implements Xoh_module_itm {
	public byte[] Key() {return Xoh_module_itm_.Key_xoui;}
	public boolean Enabled() {return enabled;} public void Enabled_y_() {enabled = true;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled;
	public void Clear() {enabled = false;}
	public Xoh_module_itm__xoui Init(Xoae_app app) {
		if (dir_url == null) {
			dir_url			= app.Fsys_mgr().Bin_any_dir().GenSubDir_nest("xowa", "html", "res", "src", "xowa", "xoui");
			url_hui_js		= dir_url.GenSubFil("xoui.js").To_http_file_bry();
		}
		return this;
	}
	public void Write_css_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public void Write_css_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public void Write_js_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {
		if (!enabled) return;
		wtr.Write_js_include(url_hui_js);
	}
	public void Write_js_head_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {
		if (!enabled) return;
		wtr.Write_js_line(Bry_.new_a7("var xoui__ctx = {"));
		wtr.Write_js_line(Bry_.new_a7("  img_dir : '" + Img_dir().To_http_file_str() + "',"));
		wtr.Write_js_line(Bry_.new_a7("  wiki    : '" + wiki.Domain_str() + "',"));
		wtr.Write_js_line(Bry_.new_a7("}"));
	}
	public void Write_js_tail_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public void Write_js_head_global(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public static Io_url Img_dir() {return dir_url.GenSubDir("img");} private static Io_url dir_url; private static byte[] url_hui_js;
}
