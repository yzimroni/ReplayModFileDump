package net.yzimroni.replaymoddump;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.replaymod.replaystudio.studio.ReplayStudio;

public class Utils {

	public static final ReplayStudio STUDIO = new ReplayStudio();
	public static final Gson GSON = new GsonBuilder().serializeNulls().create();

	static {
		// TODO Parse only certain packets?
		STUDIO.setWrappingEnabled(false);
	}

}
