// enums.Images
//

package enums;

public enum Images {
    BAGGAGE {
        @Override
        public String getImg() {
            return "images/baggage.png";
        }
    },

    BRIDGE {
        @Override
        public String getImg() {
            return "images/bridge.png";
        }
    },

    HOLDER {
        @Override
        public String getImg() {
            return "images/holder.png";
        }
    },

    SOKOBAN {
        @Override
        public String getImg() {
            return "images/sokoban.png";
        }
    },

    TERRAIN {
        @Override
        public String getImg() {
            return "images/terrain.png";
        }
    },

    WALL {
        @Override
        public String getImg() {
            return "images/wall.png";
        }
    };

    public abstract String getImg();
}

