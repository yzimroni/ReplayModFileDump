package net.yzimroni.replaymoddump;

import org.spacehq.mc.protocol.data.game.values.TitleAction;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerPlayerListDataPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerPluginMessagePacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerTitlePacket;
import org.spacehq.packetlib.packet.Packet;

import com.replaymod.replaystudio.PacketData;

public class PacketLogger {

	public void handle(PacketData data) {
		Packet packet = data.getPacket();
		String text = "";
		text += getPacketTime(data) + " ";
		String packetName = data.getPacket().getClass().getSimpleName();
		text += packetName + " ";
		if (packet instanceof ServerPluginMessagePacket) {
			// TODO It may not be a text, should check
			text += "[Plugin message text: " + new String(((ServerPluginMessagePacket) packet).getData()) + "] ";
		} else if (packet instanceof ServerChatPacket) {
			text += "[Chat message: " + ((ServerChatPacket) packet).getMessage() + "] ";
		} else if (packet instanceof ServerTitlePacket) {
			TitleAction action = ((ServerTitlePacket) packet).getAction();
			if (action == TitleAction.TITLE) {
				text += "[Title text: " + ((ServerTitlePacket) packet).getTitle() + "] ";
			} else if (action == TitleAction.SUBTITLE) {
				text += "[Subtitle text: " + ((ServerTitlePacket) packet).getSubtitle() + "] ";
			}
		} else if (packet instanceof ServerPlayerListDataPacket) {
			text += "[Header text: " + ((ServerPlayerListDataPacket) packet).getHeader() + "] ";
			text += "[Footer text: " + ((ServerPlayerListDataPacket) packet).getFooter() + "] ";
		}
		if (!packetName.contains("Chunk")) {
			try {
				text += Utils.GSON.toJson(packet);
			} catch (Exception e) {
				text += "Failed to convert packet to json: " + packet;
			}
		}
		System.out.println(text.trim());
	}

	private int getPacketTime(PacketData packet) {
		return ((int) (packet.getTime() / 50)) + 1;
	}

}
