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
package gplx.ios; import gplx.*;
import org.junit.*;
public abstract class IoEngine_fil_xfer_base {
	@Before public void setup() {
		engine = engine_();
		fx = IoEngineFxt.new_();
		setup_hook();
		src = root.GenSubFil("src.txt"); trg = root.GenSubFil("trg.txt");
	}	protected IoEngine engine; @gplx.Internal protected IoEngineFxt fx; protected Io_url src, trg, root;
	DateAdp srcModifiedTime = DateAdp_.parse_gplx("2010.04.12 20.26.01.000"), trgModifiedTime = DateAdp_.parse_gplx("2010.04.01 01.01.01.000");
	protected abstract IoEngine engine_();
	protected abstract void setup_hook();
	protected abstract Io_url AltRoot();
	@Test  @gplx.Virtual public void CopyFil() {
		fx.run_SaveFilText(src, "src"); fx.run_UpdateFilModifiedTime(src, srcModifiedTime);
		fx.tst_ExistsPaths(true, src);
		fx.tst_ExistsPaths(false, trg);

		IoEngine_xrg_xferFil.copy_(src, trg).Exec();
		fx.tst_ExistsPaths(true, src, trg);
		fx.tst_LoadFilStr(trg, "src");
		fx.tst_QueryFil_modifiedTime(trg, srcModifiedTime);
	}
	@Test  @gplx.Virtual public void CopyFil_overwrite_fail() {
		fx.run_SaveFilText(src, "src");
		fx.run_SaveFilText(trg, "trg");

		try {IoEngine_xrg_xferFil.copy_(src, trg).Exec();}
		catch (Exception exc) {Exc_.Noop(exc);
			fx.tst_ExistsPaths(true, src, trg);
			fx.tst_LoadFilStr(trg, "trg");
			return;
		}
		Tfds.Fail_expdError();
	}
	@Test  @gplx.Virtual public void CopyFil_overwrite_pass() {
		fx.run_SaveFilText(src, "src"); fx.run_UpdateFilModifiedTime(src, srcModifiedTime);
		fx.run_SaveFilText(trg, "trg"); fx.run_UpdateFilModifiedTime(trg, trgModifiedTime);

		IoEngine_xrg_xferFil.copy_(src, trg).Overwrite_().Exec();
		fx.tst_ExistsPaths(true, src, trg);
		fx.tst_LoadFilStr(trg, "src");
		fx.tst_QueryFil_modifiedTime(trg, srcModifiedTime);
	}
	@Test  @gplx.Virtual public void MoveFil() {
		fx.run_SaveFilText(src, "src");
		fx.tst_ExistsPaths(true, src);
		fx.tst_ExistsPaths(false, trg);

		IoEngine_xrg_xferFil.move_(src, trg).Exec();
		fx.tst_ExistsPaths(false, src);
		fx.tst_ExistsPaths(true, trg);
	}
	@Test  @gplx.Virtual public void MoveFil_overwrite_fail() {
		fx.run_SaveFilText(src, "src");
		fx.run_SaveFilText(trg, "trg");

		try {IoEngine_xrg_xferFil.move_(src, trg).Exec();}
		catch (Exception exc) {Exc_.Noop(exc);
			fx.tst_ExistsPaths(true, src);
			fx.tst_ExistsPaths(true, trg);
			fx.tst_LoadFilStr(trg, "trg");
			return;
		}
		Tfds.Fail_expdError();
	}
	@Test  @gplx.Virtual public void MoveFil_overwrite_pass() {
		fx.run_SaveFilText(src, "src"); fx.run_UpdateFilModifiedTime(src, srcModifiedTime);
		fx.run_SaveFilText(trg, "trg"); fx.run_UpdateFilModifiedTime(trg, trgModifiedTime);

		IoEngine_xrg_xferFil.move_(src, trg).Overwrite_().Exec();
		fx.tst_ExistsPaths(false, src);
		fx.tst_ExistsPaths(true, trg);
		fx.tst_LoadFilStr(trg, "src");
		fx.tst_QueryFil_modifiedTime(trg, srcModifiedTime);
	}
	@Test  @gplx.Virtual public void MoveFil_betweenDrives() {
		IoEngine_xrg_deleteDir.new_(AltRoot()).Recur_().ReadOnlyFails_off().Exec();
		src = root.GenSubFil_nest("dir", "fil1a.txt");
		trg = AltRoot().GenSubFil_nest("dir", "fil1b.txt");
		fx.run_SaveFilText(src, "src");
		fx.tst_ExistsPaths(true, src);
		fx.tst_ExistsPaths(false, trg);

		IoEngine_xrg_xferFil.move_(src, trg).Exec();
		fx.tst_ExistsPaths(false, src);
		fx.tst_ExistsPaths(true, trg);
	}
}
