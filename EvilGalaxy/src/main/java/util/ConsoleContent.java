package util;

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
import enums.Commands;
import extras.ConsoleForm;
import extras.Manual;
import game_engine.Difficulty;
import game_engine.InitObjects;
import game_engine.UpdateObjects;
import items.Gold;
import items.HealthPack;
import items.SaveSign;
import menu_engine.GameMenuBar;
import menu_engine.MouseInputHandler;
import menu_states.MenuState;

public class ConsoleContent extends OutputStream {

	public static ConsoleForm console;
	public static Manual manual;
	public static boolean consoleON, manualON, god;
	protected JPanel contentPane;
	protected JTextField textField;
	protected JTextArea textArea;

	public ConsoleContent() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		final JScrollPane scrollPane = new JScrollPane();

		setTextField(new JTextField());
		getTextField().setFont(getTextField().getFont().deriveFont(14f));

		getTextField().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				final int key = e.getKeyCode();

				if (key == KeyEvent.VK_ENTER && textArea.getText().trim().length() == 0) {
					textArea.append(getTextField().getText().toUpperCase() + "\n");
					getTextField().setText("");
					getTextField().requestFocusInWindow();

					// HELP
					if (Commands.HELP.name().trim().equalsIgnoreCase(textArea.getText().trim())) {
						textArea.append("********HERE IS A LIST OF ALL AVAILABLE COMMANDS:*********" + "\n"
								+ "HELP - show this list" + "\n"
								+ "EASY - restart/resume (if not in a game), or switch to EASY" + "\n"
								+ "MED - restart/resume (if not in a game), or switch to MEDIUM" + "\n"
								+ "HARD - restart/resume (if not in a game), or switch to HARD" + "\n"
								+ "RESTART - restart the game, even if a game is running" + "\n" + "PAUSE - game pause"
								+ "\n" + "GOD - enable Godmode" + "\n" + "DOG - disable Godmode" + "\n"
								+ "LEVEL2 - skip to Level 2" + "\n" + "LEVEL3 - skip to Level 3" + "\n"
								+ "LEVEL4 - skip to Level 4" + "\n" + "VOLOFF - stop game music" + "\n"
								+ "VOLON - play game music" + "\n" + "STATS - show multiple game stats" + "\n"
								+ "CLS - close console" + "\n" + "EXIT - exit game" + "\n");
						return;
					}

					// CLOSE CONSOLE
					else if (Commands.CLS.name().trim().equalsIgnoreCase(textArea.getText().trim())) {
						consoleON = false;
						if(ConsoleForm.frame != null) ConsoleForm.frame.dispose();
						ConsoleForm.frame = null;
						ConsoleContent.console = null;
						textArea.append("********Closing...*********" + "\n");
						return;
					}

					// REFRESH IPs
					else if (Commands.REFRESH.name().trim().equalsIgnoreCase(textArea.getText().trim())) {

						// Create operating system process from arpe.bat file
						// command
						final ProcessBuilder pb = new ProcessBuilder("arpe.bat");
						String out = "";

						pb.redirectErrorStream();
						// Starts a new process using the attributes of this
						// process builder
						Process p = null;
						try {
							p = pb.start();
						} catch (final IOException e1) {
							e1.printStackTrace();
						}

						final BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

						// String out is used to store output of this
						// command(process)

						while (true) {
							String l = null;
							try {
								l = br.readLine();
							} catch (final IOException ex) {
							}
							if (l == null)
								break;
							out += "\n" + l;
						}

						// A compiled representation of a regular expression
						final Pattern pattern = Pattern.compile(".*\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b");

						/*
						 * An engine that performs match operations on a character sequence by
						 * interpreting a Pattern
						 */
						final Matcher match = pattern.matcher(out);

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
							} catch (final IOException ex) {
							}
						}

						textArea.append("\n" + "********List of all local IPs:*********" + "\n" + "\n");
						textArea.append(out);

						return;
					}

					// PAUSE
					else if (Commands.PAUSE.name().trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.ingame == true) {
							InitObjects.timerEasy.stop();
							InitObjects.timerMedium.stop();
							InitObjects.timerHard.stop();
							LoadSounds.BG_MUSIC.stop();
							LoadSounds.TAUNT.stop();
							textArea.append("********Game was paused!*********" + "\n");
						}
						if (!InitObjects.ingame) {
							textArea.append("***********WARNING: Not in a game!*********");
						}
						return;
					}

					// EASY
					else if (Commands.EASY.name().trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.ingame == true && !InitObjects.timerEasy.isRunning()) {
							textArea.append("********Game switched to easy!*********" + "\n");
							InitObjects.timerMedium.stop();
							InitObjects.timerHard.stop();
							InitObjects.timerEasy.start();
							LoadSounds.BG_MUSIC.loop();
							return;
						}
						if (InitObjects.ingame == true && InitObjects.timerEasy.isRunning() == true) {
							textArea.append("********Already on E A S Y!*********" + "\n");
							return;
						}
						if (!InitObjects.ingame) {
							textArea.append("********Game initialized!*********" + "\n");
							if(MouseInputHandler.main != null) Difficulty.easy();
							return;
						}

						return;
					}

					// MEDIUM
					else if (Commands.MED.name().trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.ingame == true && !InitObjects.timerMedium.isRunning()) {
							textArea.append("********Game switched to medium!*********" + "\n");
							InitObjects.timerEasy.stop();
							InitObjects.timerHard.stop();
							InitObjects.timerMedium.start();
							LoadSounds.BG_MUSIC.loop();
							return;
						}
						if (InitObjects.ingame == true && InitObjects.timerMedium.isRunning() == true) {
							textArea.append("********Already on M E D I U M!*********" + "\n");
							return;
						}
						if (!InitObjects.ingame) {
							textArea.append("********Game initialized!*********" + "\n");
							if(MouseInputHandler.main != null) Difficulty.medium();
							return;
						}
						return;
					}

					// HARD
					else if (Commands.HARD.name().trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.ingame == true && !InitObjects.timerHard.isRunning()) {
							textArea.append("********Game switched to hard!*********" + "\n");
							InitObjects.timerEasy.stop();
							InitObjects.timerMedium.stop();
							InitObjects.timerHard.start();
							LoadSounds.BG_MUSIC.loop();
							return;
						}
						if (InitObjects.ingame == true && InitObjects.timerHard.isRunning() == true) {
							textArea.append("********Already on H A R D!*********" + "\n");
							return;
						}
						if (!InitObjects.ingame) {
							textArea.append("********Game initialized!*********" + "\n");
							if(MouseInputHandler.main != null) Difficulty.hard();
							return;
						}
						return;
					}

					// EXIT
					else if (Commands.EXIT.name().trim().equalsIgnoreCase(textArea.getText().trim())) {
//						System.exit(0);
						MenuState.isOn = false;
						LoadSounds.BG_MUSIC.stop();
						LoadSounds.HIT.stop();
						LoadSounds.TAUNT.stop();
						InitObjects.ingame = false;
						if(MouseInputHandler.main != null) MouseInputHandler.main.dispose();
						MouseInputHandler.main = null;
						MenuState.isOn = true;
						textArea.append("********Game exited!*********" + "\n");
						return;
					}

					// VOLUME OFF
					else if (Commands.VOLOFF.name().trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
								|| InitObjects.timerHard.isRunning() == true) {
							LoadSounds.BG_MUSIC.stop();
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

					// VOLUME ON
					else if (Commands.VOLON.name().trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
								|| InitObjects.timerHard.isRunning() == true) {
							LoadSounds.BG_MUSIC.loop();
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

					// GODMODE ON
					else if (Commands.GOD.name().trim().equalsIgnoreCase(textArea.getText().trim())) {
						if ((InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
								|| InitObjects.timerHard.isRunning() == true) && UpdateObjects.lifePlayerShip >= 3) {
							god = true;
							UpdateObjects.lifePlayerShip = -999;
							textArea.append("********GODMODE ON*********" + "\n");
							TextToSpeech.playVoice("GODLIKE!");
							return;
						}
						if (InitObjects.ingame == true && UpdateObjects.lifePlayerShip < 3) {
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

					// GODMODE OFF
					else if (Commands.GODOFF.name().trim().equalsIgnoreCase(textArea.getText().trim())) {
						if ((InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
								|| InitObjects.timerHard.isRunning() == true) && UpdateObjects.lifePlayerShip < 3) {
							god = false;
							UpdateObjects.lifePlayerShip = 3;
							textArea.append("********GODMODE OFF*********" + "\n");
							TextToSpeech.playVoice("Healthy!");
							return;
						}
						if (InitObjects.ingame == true && UpdateObjects.lifePlayerShip >= 3) {
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

					// STATS
					else if (Commands.STATS.name().trim().equalsIgnoreCase(textArea.getText().trim())) {
						if (InitObjects.ingame == true) {
							if (Alien.aliens.size() > 0) {
								if (InitObjects.timerEasy.isRunning() == true) {
									textArea.append("Level: 1" + "\n");
									textArea.append("Difficulty: Easy" + "\n");
									textArea.append("Alien.aliens killed: " + (-(Alien.aliens.size() - 54)) + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifePlayerShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifePlayerShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifePlayerShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifePlayerShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifePlayerShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}

									return;
								}

								if (InitObjects.timerMedium.isRunning() == true) {
									textArea.append("Level: 1" + "\n");
									textArea.append("Difficulty: Medium" + "\n");
									textArea.append("Alien.aliens killed: " + (-(Alien.aliens.size() - 54)) + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifePlayerShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifePlayerShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifePlayerShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifePlayerShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifePlayerShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerHard.isRunning() == true) {
									textArea.append("Level: 1" + "\n");
									textArea.append("Difficulty: Hard" + "\n");
									textArea.append("Alien.aliens killed: " + (-(Alien.aliens.size() - 54)) + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifePlayerShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifePlayerShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifePlayerShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifePlayerShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifePlayerShip == 3) {
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
									if (UpdateObjects.lifePlayerShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifePlayerShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifePlayerShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifePlayerShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifePlayerShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerMedium.isRunning() == true) {
									textArea.append("Level: 2" + "\n");
									textArea.append("Difficulty: Medium" + "\n");
									textArea.append("Dragons killed: " + (-(Dragon.dragons.size() - 30)) + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifePlayerShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifePlayerShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifePlayerShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifePlayerShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifePlayerShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerHard.isRunning() == true) {
									textArea.append("Level: 2" + "\n");
									textArea.append("Difficulty: Hard" + "\n");
									textArea.append("Dragons killed: " + (-(Dragon.dragons.size() - 30)) + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifePlayerShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifePlayerShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifePlayerShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifePlayerShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifePlayerShip == 3) {
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
									if (UpdateObjects.lifePlayerShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifePlayerShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifePlayerShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifePlayerShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifePlayerShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerMedium.isRunning() == true) {
									textArea.append("Level: 3" + "\n");
									textArea.append("Difficulty: Medium" + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifePlayerShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifePlayerShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifePlayerShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifePlayerShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifePlayerShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerHard.isRunning() == true) {
									textArea.append("Level: 3" + "\n");
									textArea.append("Difficulty: Hard" + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifePlayerShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifePlayerShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifePlayerShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifePlayerShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifePlayerShip == 3) {
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
									if (Gold.goldstack.isEmpty()) {
										if (UpdateObjects.lifeEvilHead < 10) {
											textArea.append("lifeEvilHead: 100 %" + "\n");
										}
										if (UpdateObjects.lifeEvilHead >= 10 && UpdateObjects.lifeEvilHead < 20) {
											textArea.append("lifeEvilHead: 80 %" + "\n");
										}
										if (UpdateObjects.lifeEvilHead >= 20 && UpdateObjects.lifeEvilHead < 30) {
											textArea.append("lifeEvilHead: 60 %" + "\n");
										}
										if (UpdateObjects.lifeEvilHead >= 30 && UpdateObjects.lifeEvilHead < 40) {
											textArea.append("lifeEvilHead: 40 %" + "\n");
										}
										if (UpdateObjects.lifeEvilHead >= 40 && UpdateObjects.lifeEvilHead < 50) {
											textArea.append("lifeEvilHead: 20 %" + "\n");
										}
									}
									if (UpdateObjects.lifePlayerShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifePlayerShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifePlayerShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifePlayerShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifePlayerShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerMedium.isRunning() == true) {
									textArea.append("Level: 4" + "\n");
									textArea.append("Difficulty: Medium" + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifePlayerShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifePlayerShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifePlayerShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifePlayerShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifePlayerShip == 3) {
										textArea.append("\n" + "Godmode: " + "OFF");
									}
									return;
								}

								if (InitObjects.timerHard.isRunning() == true) {
									textArea.append("Level: 4" + "\n");
									textArea.append("Difficulty: Hard" + "\n");
									textArea.append("Healthpacks left: " + HealthPack.healthpack.size() + "\n");
									if (UpdateObjects.lifePlayerShip <= 4) {
										textArea.append("Lifestats: Healthy");
									}
									if (UpdateObjects.lifePlayerShip == 5) {
										textArea.append("Lifestats: Injured");
									}
									if (UpdateObjects.lifePlayerShip == 6) {
										textArea.append("Lifestats: Critical");
									}
									if (UpdateObjects.lifePlayerShip < 3) {
										textArea.append("\n" + "Godmode: " + "ON");
									}
									if (UpdateObjects.lifePlayerShip == 3) {
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

					// RESTART a.k.a. LEVEL 1
					else if (Commands.RESTART.name().trim().equalsIgnoreCase(textArea.getText().trim())) {

						textArea.append("********Game initialized!*********" + "\n");
						if(MouseInputHandler.main != null) Difficulty.restart();
						return;
					}

					// LEVEL 2
					else if (Commands.LEVEL2.name().trim().equalsIgnoreCase(textArea.getText().trim())) {

						if (InitObjects.ingame == true) {
							textArea.append("********Level 2 was loaded!*********" + "\n");

							if(MouseInputHandler.main != null) Difficulty.restart();
							Alien.aliens.clear();
							LoadSounds.TAUNT.loop();
							if (TextToSpeech.voiceInterruptor == false) {
								TextToSpeech.playVoice("Level 2!");
								TextToSpeech.voiceInterruptor = true;
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

					// LEVEL 3
					else if (Commands.LEVEL3.name().trim().equalsIgnoreCase(textArea.getText().trim())) {

						if (InitObjects.ingame == true) {
							if(MouseInputHandler.main != null) Difficulty.restart();
							Alien.aliens.clear();
							Dragon.dragons.clear();
							LoadSounds.TAUNT.stop();
							if (TextToSpeech.voiceInterruptor == false) {
								TextToSpeech.playVoice("Level 3!");
								TextToSpeech.voiceInterruptor = true;
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

					// LEVEL 4
					else if (Commands.LEVEL4.name().trim().equalsIgnoreCase(textArea.getText().trim())) {

						if (InitObjects.ingame == true) {
							if(MouseInputHandler.main != null) Difficulty.restart();
							Alien.aliens.clear();
							Dragon.dragons.clear();
							LoadSounds.TAUNT.stop();
							UpdateObjects.lifeBunker = 50;
							if (TextToSpeech.voiceInterruptor == false) {
								TextToSpeech.playVoice("Level 4!");
								TextToSpeech.voiceInterruptor = true;
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

					// SAVE
					else if (Commands.SAVE.name().trim().equalsIgnoreCase(textArea.getText().trim())) {

						TextToSpeech.voiceInterruptor = false;

						if (InitObjects.ingame == true) {

							if (Alien.aliens.size() > 0 && TextToSpeech.voiceInterruptor == false) {

								GameMenuBar.savedOnL2 = false;
								GameMenuBar.savedOnL3 = false;
								GameMenuBar.savedOnL4 = false;
								GameMenuBar.savedOnL1 = true;
								TextToSpeech.playVoice("Game saved!");
								TextToSpeech.voiceInterruptor = true;

								if (SaveSign.saveSign != null) {
									SaveSign.saveSign.initSave();
									SaveSign.saveSign.setVisible(true);
								}
							}

							TextToSpeech.voiceInterruptor = false;

							if (Alien.aliens.isEmpty() && Dragon.dragons.size() > 0
									&& TextToSpeech.voiceInterruptor == false) {

								GameMenuBar.savedOnL1 = false;
								GameMenuBar.savedOnL3 = false;
								GameMenuBar.savedOnL4 = false;
								GameMenuBar.savedOnL2 = true;
								TextToSpeech.playVoice("Game saved!");
								TextToSpeech.voiceInterruptor = true;

								if (SaveSign.saveSign != null) {
									SaveSign.saveSign.initSave();
									SaveSign.saveSign.setVisible(true);
								}
							}

							TextToSpeech.voiceInterruptor = false;

							if (Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker < 50
									&& TextToSpeech.voiceInterruptor == false) {

								GameMenuBar.savedOnL2 = false;
								GameMenuBar.savedOnL1 = false;
								GameMenuBar.savedOnL4 = false;
								GameMenuBar.savedOnL3 = true;
								TextToSpeech.playVoice("Game saved!");
								TextToSpeech.voiceInterruptor = true;

								if (SaveSign.saveSign != null) {
									SaveSign.saveSign.initSave();
									SaveSign.saveSign.setVisible(true);
								}
							}

							TextToSpeech.voiceInterruptor = false;

							if (UpdateObjects.lifeBunker == 50 && TextToSpeech.voiceInterruptor == false) {

								GameMenuBar.savedOnL1 = false;
								GameMenuBar.savedOnL2 = false;
								GameMenuBar.savedOnL3 = false;
								GameMenuBar.savedOnL4 = true;
								TextToSpeech.playVoice("Game saved!");
								TextToSpeech.voiceInterruptor = true;

								if (SaveSign.saveSign != null) {
									SaveSign.saveSign.initSave();
									SaveSign.saveSign.setVisible(true);
								}
							}
						}
						return;
					}

					// AUTOSAVE
					/*else if (COMMANDS[18].trim().equalsIgnoreCase(textArea.getText().trim()) && 
							GameMenuBar.autosave.isSelected() == false) {
						GameMenuBar.autosave.setSelected(true);
						if (SaveSign.saveSign != null) {
							SaveSign.saveSign.initSave();
							SaveSign.saveSign.setVisible(true);
						}
						return;
					}*/

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
		final GroupLayout gl_contentPane = new GroupLayout(contentPane);
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

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	@Override
	public void write(int b) throws IOException {
		textArea.append(String.valueOf((char) b));
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}