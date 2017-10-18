package net.yzimroni.replaymoddump;

import java.io.File;
import java.io.FileInputStream;

import com.google.common.base.Preconditions;
import com.replaymod.replaystudio.PacketData;
import com.replaymod.replaystudio.replay.Replay;

public class ReplayModDumpMain {

	public static void main(String[] args) {
		Preconditions.checkArgument(args.length == 1, "Not enough arguments");
		File replayFile = new File(args[0]);
		Preconditions.checkArgument(replayFile.exists(), "File doesn't exists");
		Replay replay = loadReplay(replayFile);
		PacketLogger logger = new PacketLogger();
		for (PacketData packet : replay) {
			logger.handle(packet);
		}
	}

	private static Replay loadReplay(File f) {
		try {
			if (Utils.STUDIO
					.isCompatible(Utils.STUDIO.readReplayMetaData(new FileInputStream(f)).getFileFormatVersion())) {
				Replay replay = Utils.STUDIO.createReplay(new FileInputStream(f));
				return replay;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
