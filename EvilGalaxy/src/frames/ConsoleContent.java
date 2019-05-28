package frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import entities.Alien;
import entities.Dragon;
import game_engine.Difficulty;
import game_engine.DrawScene;
import game_engine.InitObjects;
import game_engine.UpdateObjects;
import items.HealthPack;
import sound_engine.LoadSounds;

@SuppressWarnings("serial")
public class ConsoleContent extends OutputStream {

	protected JPanel contentPane;
	protected static JTextField textField;
	protected JTextArea textArea;
	static String out = "";
	protected static String[] commands;

	public ConsoleContent() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		JScrollPane scrollPane = new JScrollPane();

		setTextField(new JTextField());
		getTextField().setFont(getTextField().getFont().deriveFont(14f));

		commands = new String[17];
		commands[0] = "help";
		commands[1] = "cls";
		commands[2] = "refresh";
		commands[3] = "pause";
		commands[4] = "easy";
		commands[5] = "med";
		commands[6] = "hard";
		commands[7] = "exit";
		commands[8] = "voloff";
		commands[9] = "volon";
		commands[10] = "god";
		commands[11] = "dog";
		commands[12] = "stats";
		commands[13] = "restart";
		commands[14] = "level2";
		commands[15] = "level3";
		commands[16] = "level4";

		getTextField().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				int key = e.getKeyCode();

				if (key == KeyEvent.VK_ENTER && textArea.getText().trim().length() == 0) {
					textArea.append(getTextField().getText().toUpperCase() + "\n");
					getTextField().setText("");
					getTextField().requestFocusInWindow();

					if (commands[0].trim().equalsIgnoreCase(textArea.getText().trim())) {
						textArea.append("********HERE IS A LIST OF ALL AVAILABLE COMMANDS:*********" + "\n");
						textArea.append("HELP - show this list" + "\n");
						textArea.append("EASY - restart/resume (if not in a game), or switch to EASY" + "\n");
						textArea.append("MED - restart/resume (if not in a game), or switch to MEDIUM" + "\n");
						textArea.append("HARD - restart/resume (if not in a game), or switch to HARD" + "\n");
						textArea.append("RESTART - restart the game, even if a game is running" + "\n");
						textArea.append("PAUSE - game pause" + "\n");
						textArea.append("GOD - enable Godmode" + "\n");
						textArea.append("DOG - disable Godmode" + "\n");
						textArea.append("LEVEL2 - skip to Level 2" + "\n");
						textArea.append("LEVEL3 - skip to Level 3" + "\n");
						textArea.append("LEVEL4 - skip to Level 4" + "\n");
						textArea.append("VOLOFF - stop game music" + "\n");
						textArea.append("VOLON - play game music" + "\n");
						textArea.append("STATS - show multiple game stats" + "\n");
						textArea.append("CLS - close console" + "\n");
						textArea.append("EXIT - exit game" + "\n");
						return;
					}

					if (commands[1].trim().equalsIgnoreCase(textArea.getText().trim())) {
						InitObjects.consoleON = false;
//						dispose();
						textArea.append("********Closing...*********" + "\n");
						return;
					}

					if (commands[2].trim().equalsIgnoreCase(textArea.getText().trim())) {

						// Create operating system process from arpe.bat file
						// command
						ProcessBuilder pb = new ProcessBuilder("arpe.bat");

						pb.redirectErrorStream();
						// Starts a new process using the attributes of this
						// process builder
						Process p = null;
						try {
							p = pb.start();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

						// String out is used to store output of this
						// command(process)

						while (true) {
							String l = null;
							try {
								l = br.readLine();
							} catch (IOException ex) {
							}
							if (l == null)
								break;
							out += "\n" + l;
						}

						// A compiled representation of a regular expression
						Pattern pattern = Pattern.compile(".*\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b");

						/*
						 * An engine that performs match operations on a character sequence by
						 * interpreting a Pattern
						 */
						Matcher match = pattern.matcher(out);

						out = "";
						String pLoc;

						if (!(match.find())) // In case no IP address Found in
												// out
							out = "No IP found!";

						else {

							/*
							 * Returns the input subsequence matched by the previous match in this case IP
							 * of our interface
							 */
							pLoc = match.group();

							out += pLoc;
							while (match.find()) {
								pLoc = match.group(); // Returns the IP of other
														// hosts
								out += pLoc + "\n";
							}
							try {
								br.close();
							} catch (IOException ex) {
							}
						}

						textArea.append("\n" + "********List of all local IPs:*********" + "\n" + "\n");
						textArea.append(out);

						return;
					}

					if (commands[3].trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.ingame == true) {
							InitObjects.timerEasy.stop();
							InitObjects.timerMedium.stop();
							InitObjects.timerHard.stop();
							LoadSounds.bgMusic.stop();
							LoadSounds.roar.stop();
							textArea.append("********Game was paused!*********" + "\n");
						}
						if (!InitObjects.ingame) {
							textArea.append("***********WARNING: Not in a game!*********");
						}
						return;
					}

					if (commands[4].trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.ingame == true && !InitObjects.timerEasy.isRunning()) {
							textArea.append("********Game switched to easy!*********" + "\n");
							InitObjects.timerMedium.stop();
							InitObjects.timerHard.stop();
							InitObjects.timerEasy.start();
							LoadSounds.bgMusic.loop();
							return;
						}
						if (InitObjects.ingame == true && InitObjects.timerEasy.isRunning() == true) {
							textArea.append("********Already on E A S Y!*********" + "\n");
							return;
						}
						if (!InitObjects.ingame) {
							textArea.append("********Game initialized!*********" + "\n");
							Difficulty.easy();
							return;
						}

						return;
					}

					if (commands[5].trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.ingame == true && !InitObjects.timerMedium.isRunning()) {
							textArea.append("********Game switched to medium!*********" + "\n");
							InitObjects.timerEasy.stop();
							InitObjects.timerHard.stop();
							InitObjects.timerMedium.start();
							LoadSounds.bgMusic.loop();
							return;
						}
						if (InitObjects.ingame == true && InitObjects.timerMedium.isRunning() == true) {
							textArea.append("********Already on M E D I U M!*********" + "\n");
							return;
						}
						if (!InitObjects.ingame) {
							textArea.append("********Game initialized!*********" + "\n");
							Difficulty.medium();
							return;
						}
						return;
					}

					if (commands[6].trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.ingame == true && !InitObjects.timerHard.isRunning()) {
							textArea.append("********Game switched to hard!*********" + "\n");
							InitObjects.timerEasy.stop();
							InitObjects.timerMedium.stop();
							InitObjects.timerHard.start();
							LoadSounds.bgMusic.loop();
							return;
						}
						if (InitObjects.ingame == true && InitObjects.timerHard.isRunning() == true) {
							textArea.append("********Already on H A R D!*********" + "\n");
							return;
						}
						if (!InitObjects.ingame) {
							textArea.append("********Game initialized!*********" + "\n");
							Difficulty.hard();
							return;
						}
						return;
					}

					if (commands[7].trim().equalsIgnoreCase(textArea.getText().trim())) {
						System.exit(0);
						textArea.append("********Exiting...*********" + "\n");
						return;
					}

					if (commands[8].trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
								|| InitObjects.timerHard.isRunning() == true) {
							LoadSounds.bgMusic.stop();
							textArea.append("********MUSIC IS OFF*********" + "\n");
							return;
						}
						if (!InitObjects.ingame || (!InitObjects.timerEasy.isRunning()
								&& !InitObjects.timerMedium.isRunning() && !InitObjects.timerHard.isRunning())) {
							textArea.append("********WARNING: Not in a game!*********" + "\n");
							return;
						}

						return;
					}

					if (commands[9].trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
								|| InitObjects.timerHard.isRunning() == true) {
							LoadSounds.bgMusic.loop();
							textArea.append("********MUSIC IS ON*********" + "\n");
							return;
						}
						if (!InitObjects.ingame || (!InitObjects.timerEasy.isRunning()
								&& !InitObjects.timerMedium.isRunning() && !InitObjects.timerHard.isRunning())) {
							textArea.append("********WARNING: Not in a game!*********" + "\n");
							return;
						}
						return;
					}

					if (commands[10].trim().equalsIgnoreCase(textArea.getText().trim())) {
						if ((InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
								|| InitObjects.timerHard.isRunning() == true) && UpdateObjects.lifeMyShip >= 3) {
							InitObjects.god = true;
							UpdateObjects.lifeMyShip = -999;
							textArea.append("********GODMODE ON*********" + "\n");
							DrawScene.initVoice("GODLIKE!");
							return;
						}
						if (InitObjects.ingame == true && UpdateObjects.lifeMyShip < 3) {
							textArea.append("***********Already in GODMODE!*********" + "\n");
							return;
						}
						if (!InitObjects.ingame || (!InitObjects.timerEasy.isRunning()
								&& !InitObjects.timerMedium.isRunning() && !InitObjects.timerHard.isRunning())) {
							textArea.append("********WARNING: Not in a game!*********" + "\n");
							return;
						}
						return;
					}

					if (commands[11].trim().equalsIgnoreCase(textArea.getText().trim())) {
						if ((InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
								|| InitObjects.timerHard.isRunning() == true) && UpdateObjects.lifeMyShip < 3) {
							InitObjects.god = false;
							UpdateObjects.lifeMyShip = 3;
							textArea.append("********GODMODE OFF*********" + "\n");
							DrawScene.initVoice("Healthy!");
							return;
						}
						if (InitObjects.ingame == true && UpdateObjects.lifeMyShip >= 3) {
							textArea.append("***********Not in a GODMODE!*********" + "\n");
							return;
						}
						if (!InitObjects.ingame || (!InitObjects.timerEasy.isRunning()
								&& !InitObjects.timerMedium.isRunning() && !InitObjects.timerHard.isRunning())) {
							textArea.append("********WARNING: Not in a game!*********" + "\n");
							return;
						}
						return;
					}

					if (commands[12].trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.ingame == true) {
							if (Alien.aliens.size() > 0) {
								if (InitObjects.timerEasy.isRunning() == true) {
									textArea.append("Level: 1" + "\n");
									textArea.append("Difficulty: Easy" + "\n");
									textArea.append("Alien.aliens killed: " + (-(Alien.aliens.size() - 54)) + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifeMyShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifeMyShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifeMyShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifeMyShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifeMyShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}

									return;
								}

								if (InitObjects.timerMedium.isRunning() == true) {
									textArea.append("Level: 1" + "\n");
									textArea.append("Difficulty: Medium" + "\n");
									textArea.append("Alien.aliens killed: " + (-(Alien.aliens.size() - 54)) + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifeMyShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifeMyShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifeMyShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifeMyShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifeMyShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerHard.isRunning() == true) {
									textArea.append("Level: 1" + "\n");
									textArea.append("Difficulty: Hard" + "\n");
									textArea.append("Alien.aliens killed: " + (-(Alien.aliens.size() - 54)) + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifeMyShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifeMyShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifeMyShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifeMyShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifeMyShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}
							}

							if (Alien.aliens.isEmpty() && Dragon.dragons.size() > 0) {
								if (InitObjects.timerEasy.isRunning() == true) {
									textArea.append("Level: 2" + "\n");
									textArea.append("Difficulty: Easy" + "\n");
									textArea.append("Dragons killed: " + (-(Dragon.dragons.size() - 30)) + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifeMyShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifeMyShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifeMyShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifeMyShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifeMyShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerMedium.isRunning() == true) {
									textArea.append("Level: 2" + "\n");
									textArea.append("Difficulty: Medium" + "\n");
									textArea.append("Dragons killed: " + (-(Dragon.dragons.size() - 30)) + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifeMyShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifeMyShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifeMyShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifeMyShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifeMyShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerHard.isRunning() == true) {
									textArea.append("Level: 2" + "\n");
									textArea.append("Difficulty: Hard" + "\n");
									textArea.append("Dragons killed: " + (-(Dragon.dragons.size() - 30)) + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifeMyShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifeMyShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifeMyShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifeMyShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifeMyShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}
							}

							if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker < 50) {
								if (InitObjects.timerEasy.isRunning() == true) {
									textArea.append("Level: 3" + "\n");
									textArea.append("Difficulty: Easy" + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifeMyShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifeMyShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifeMyShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifeMyShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifeMyShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerMedium.isRunning() == true) {
									textArea.append("Level: 3" + "\n");
									textArea.append("Difficulty: Medium" + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifeMyShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifeMyShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifeMyShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifeMyShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifeMyShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerHard.isRunning() == true) {
									textArea.append("Level: 3" + "\n");
									textArea.append("Difficulty: Hard" + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifeMyShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifeMyShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifeMyShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifeMyShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifeMyShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

							}

							if (UpdateObjects.lifeBunker == 50) {
								if (InitObjects.timerEasy.isRunning() == true) {
									textArea.append("Level: 4" + "\n");
									textArea.append("Difficulty: Easy" + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifeMyShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifeMyShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifeMyShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifeMyShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifeMyShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerMedium.isRunning() == true) {
									textArea.append("Level: 4" + "\n");
									textArea.append("Difficulty: Medium" + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifeMyShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifeMyShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifeMyShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifeMyShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifeMyShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerHard.isRunning() == true) {
									textArea.append("Level: 4" + "\n");
									textArea.append("Difficulty: Hard" + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifeMyShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifeMyShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifeMyShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifeMyShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifeMyShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

							}

						}

						if (!InitObjects.ingame || (!InitObjects.timerEasy.isRunning()
								&& !InitObjects.timerMedium.isRunning() && !InitObjects.timerHard.isRunning())) {
							textArea.append("***********WARNING: Not in a game!*********");
						}
						return;
					}

					if (commands[13].trim().equalsIgnoreCase(textArea.getText().trim())) {

						textArea.append("********Game initialized!*********" + "\n");
						Difficulty.restart();
						return;
					}

					if (commands[14].trim().equalsIgnoreCase(textArea.getText().trim())) {

						if (InitObjects.ingame == true) {
							textArea.append("********Level 2 was loaded!*********" + "\n");

							Difficulty.restart();
							Alien.aliens.clear();
							LoadSounds.roar.loop();
							if (DrawScene.voiceInterruptor == false) {
								DrawScene.initVoice("Level 2!");
								DrawScene.voiceInterruptor = true;
								return;
							}
							return;
						}

						if (Alien.aliens.size() == 0 && Dragon.dragons.size() > 0 && InitObjects.ingame == true) {

							textArea.append("***********Already in Level 2!*********" + "\n");
							return;
						}

						if (!InitObjects.ingame) {

							textArea.append("***********WARNING: Not in a game!*********");
							return;
						}

						return;
					}

					if (commands[15].trim().equalsIgnoreCase(textArea.getText().trim())) {

						if (InitObjects.ingame == true) {
							Difficulty.restart();
							Alien.aliens.clear();
							Dragon.dragons.clear();
							LoadSounds.roar.stop();
							if (DrawScene.voiceInterruptor == false) {
								DrawScene.initVoice("Level 3!");
								DrawScene.voiceInterruptor = true;
								return;
							}
							textArea.append("********Level 3 was loaded!*********" + "\n");
							return;
						}

						if (Dragon.dragons.size() == 0 && UpdateObjects.lifeBunker < 50 && InitObjects.ingame == true) {

							textArea.append("***********Already in Level 3!*********" + "\n");
							return;
						}

						if (!InitObjects.ingame) {

							textArea.append("***********WARNING: Not in a game!*********");
						}

						return;
					}

					if (commands[16].trim().equalsIgnoreCase(textArea.getText().trim())) {

						if (InitObjects.ingame == true) {
							Difficulty.restart();
							Alien.aliens.clear();
							Dragon.dragons.clear();
							LoadSounds.roar.stop();
							UpdateObjects.lifeBunker = 50;
							if (DrawScene.voiceInterruptor == false) {
								DrawScene.initVoice("Level 4!");
								DrawScene.voiceInterruptor = true;
								return;
							}
							textArea.append("********Level 4 was loaded!*********" + "\n");
							return;
						}

						if (UpdateObjects.lifeBunker == 50 && InitObjects.ingame == true) {

							textArea.append("***********Already in Level 4!*********" + "\n");
							return;
						}

						if (!InitObjects.ingame) {

							textArea.append("***********WARNING: Not in a game!*********");
						}

						return;
					}

					else {
						textArea.append("********No such command!*********" + "\n");
					}

				} else {
					textArea.setText("");
					return;
				}

			}
		});
		getTextField().setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
				.addComponent(getTextField(), GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(getTextField(), GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)));

		textArea = new JTextArea();
		textArea.setFont(textArea.getFont().deriveFont(14f));
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setForeground(Color.WHITE);
		textArea.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		textArea.setEditable(false);
		textArea.setText(
				"***************WELCOME TO THE GAME CONSOLE!***********\n*********TYPE HELP TO LIST ALL AVAILABLE COMMANDS!*********"
						+ "\n");
		scrollPane.setViewportView(textArea);
		contentPane.setLayout(gl_contentPane);
	}

	public static JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		ConsoleContent.textField = textField;
	}

	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		textArea.append(String.valueOf((char) b));

		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}