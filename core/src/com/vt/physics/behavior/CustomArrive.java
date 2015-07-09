package com.vt.physics.behavior;

import com.badlogic.gdx.ai.steer.Limiter;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Fck.r.sns on 09.07.2015.
 */
public class CustomArrive extends Arrive<Vector2> {
    public CustomArrive(Steerable<Vector2> owner, Steerable<Vector2> target) {
        super(owner, target);
    }

    @Override
    protected SteeringAcceleration<Vector2> arrive(SteeringAcceleration<Vector2> steering, Vector2 targetPosition) {
        // Get the direction and distance to the target
        Vector2 toTarget = steering.linear.set(targetPosition).sub(owner.getPosition());
        float distance = toTarget.len();

        // Check if we are there, return no steering
        if (distance <= arrivalTolerance) return steering.setZero();

        Limiter actualLimiter = getActualLimiter();
        // Go max speed
        float targetSpeed = actualLimiter.getMaxLinearSpeed();

        // If we are inside the slow down radius calculate a scaled speed
        if (distance <= decelerationRadius)
            targetSpeed *= distance / decelerationRadius * 0.75f + 0.25f;

        // Target velocity combines speed and direction
        Vector2 targetVelocity = toTarget.scl(targetSpeed / distance); // Optimized code for: toTarget.nor().scl(targetSpeed)

        // Acceleration tries to get to the target velocity without exceeding max acceleration
        // Notice that steering.linear and targetVelocity are the same vector
        targetVelocity.sub(owner.getLinearVelocity()).scl(1f / timeToTarget).limit(actualLimiter.getMaxLinearAcceleration());

        // No angular acceleration
        steering.angular = 0f;

        // Output the steering
        return steering;
    }
}
