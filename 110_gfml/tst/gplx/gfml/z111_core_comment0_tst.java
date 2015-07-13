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
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z111_core_comment0_tst {
	GfmlParse_fxt fx = GfmlParse_fxt.new_();
	@Before public void setup() {
		fx.ini_RootLxr_Add
			(	GfmlDocLxrs.NdeInline_lxr()
			,	GfmlDocLxrs.Whitespace_lxr()	// add whitespace to make sure it has no effect
			,	GfmlDocLxrs.Comment0_lxr()		// bgn=// end=\n
			);
	}
	@Test  public void Basic() {
		fx.tst_Doc("//a" + String_.Lf);
		fx.tst_Tkn("//a" + String_.Lf
			, fx.tkn_grp_ary_("//", "a", String_.Lf)
			);
	}
	@Test  public void Data() {
		fx.tst_Doc("a;//b" + String_.Lf, fx.nde_().Atru_("a"));
		fx.tst_Tkn("a;//b" + String_.Lf
			,	fx.tkn_grp_
			(		fx.tkn_grp_ary_("a")
			,		fx.tkn_itm_(";"))
			,	fx.tkn_grp_ary_("//", "b", String_.Lf)
			);
	}
//		@Test  public void DanglingBgn() {
//			try {
//				fx.tst_Err("//", GfmlOutCmds.Frame_danglingBgn_Err_());
//				Tfds.Fail_expdError();
//			}
//			catch (Exception exc) {Exc_.Noop(exc);}
//		}
}
