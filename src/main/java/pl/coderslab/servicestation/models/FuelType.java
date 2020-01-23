package pl.coderslab.servicestation.models;

public enum FuelType {
    DIESEL{
        @Override
        public String toString(){
            return "DIESEL";
        }
    },
    PETROL{
        @Override
        public String toString(){
            return "PETROL";
        }
    },
    LPG{
        @Override
        public String toString(){
            return "LPG";
        }
    },
    ELECTRIC{
        @Override
        public String toString(){
            return "ELECTRIC";
        }
    },
    HYBRID{
        @Override
        public String toString(){
            return "HYBRID";
        }
    }

}
