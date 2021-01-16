// enums.SoundEffects
//

package enums;

public enum SoundEffects {
    BACKMUSIC {
        @Override
        public String getSound() {
            return "sounds/backmusic.wav";
        }
    },

    BOING {
        @Override
        public String getSound() {
            return "sounds/boing.wav";
        }
    },

    CORRECT {
        @Override
        public String getSound() {
            return "sounds/correct.wav";
        }
    },

    DEAD {
        @Override
        public String getSound() {
            return "sounds/dead.wav";
        }
    },

    DENIED {
        @Override
        public String getSound() {
            return "sounds/denied.wav";
        }
    },

    HIGHSC {
        @Override
        public String getSound() {
            return "sounds/highsc.wav";
        }
    },

    MOVE {
        @Override
        public String getSound() {
            return "sounds/move.wav";
        }
    };

    public abstract String getSound();
