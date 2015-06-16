package de.jsauerwein.fitcircle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j.a.n.s on 11.11.2014.
 */
public class Exercise {
    private final int workoutType;
    private final String workoutName;
    private final List<Integer> tools;

    private Exercise (Builder builder) {
        this.workoutType = builder.workoutType;
        this.workoutName = builder.workoutName;
        this.tools = builder.tools;
    }

    public int getWorkoutType() {
        return this.workoutType;
    }

    public String getName() {
        return this.workoutName;
    }

    public static class Builder {
        private int workoutType;
        private String workoutName;
        private List<Integer> tools;

        public Builder() {
            this.tools = new ArrayList<Integer>();
        }

        public Builder type(int workoutType) {
            this.workoutType = workoutType;
            return this;
        }

        public Builder name(String workoutName) {
            this.workoutName = workoutName;
            return this;
        }

        public Builder addTool(int tool) {
            this.tools.add(tool);
            return this;
        }

        public Exercise build() {
            return new Exercise(this);
        }
    }
}
