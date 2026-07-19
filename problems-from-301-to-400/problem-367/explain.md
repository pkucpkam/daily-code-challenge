# 478. Generate Random Point in a Circle

## Intuition

Generating a random point inside a circle of a given radius `R` and center `(x, y)` requires us to pick two values: the distance from the center, and the angle. We can use polar coordinates to do this.

If we simply pick a random distance `r` between `0` and `R`, and a random angle `theta` between `0` and `2 * PI`, the points will be clustered towards the center of the circle. This is because the area of the outer rings of the circle is larger than the inner rings, so to get a uniform distribution, more points should be generated at larger distances from the center.

## Polar Coordinates and Inverse Transform Sampling

To get a uniform distribution of points across the area of the circle, the probability of picking a radius `r` should be proportional to the area of the circle with radius `r`. Since the area is proportional to the square of the radius (`Area = PI * r^2`), we need to take the square root of the randomly generated value to get the correct distance from the center.

1. Generate a random angle `theta` uniformly between `0` and `2 * PI`:
   `theta = Math.random() * 2 * Math.PI`
2. Generate a random radius `r` by taking the square root of a uniform random variable between 0 and 1, then multiplying by the given `radius`:
   `r = Math.sqrt(Math.random()) * radius`
3. Convert the polar coordinates `(r, theta)` back to Cartesian coordinates `(x, y)` and add the circle's center coordinates:
   `x = x_center + r * Math.cos(theta)`
   `y = y_center + r * Math.sin(theta)`

## Algorithm

1. Store the given `radius`, `x_center`, and `y_center` in the class.
2. In the `randPoint` method:
   - Calculate the distance `len` from the center: `Math.sqrt(Math.random()) * radius`.
   - Calculate the angle `deg`: `Math.random() * 2 * Math.PI`.
   - Compute the `x` coordinate: `x_center + len * Math.cos(deg)`.
   - Compute the `y` coordinate: `y_center + len * Math.sin(deg)`.
   - Return the array `[x, y]`.

## Complexity Analysis

### Time Complexity: $\mathcal{O}(1)$
- We perform a constant number of mathematical operations (`Math.random`, `Math.sqrt`, `Math.cos`, `Math.sin`), which take $\mathcal{O}(1)$ time. Thus, the overall time complexity for generating a random point is $\mathcal{O}(1)$.

### Space Complexity: $\mathcal{O}(1)$
- We only use a few variables to store the center and radius of the circle, as well as a few temporary variables in the `randPoint` method. This requires $\mathcal{O}(1)$ extra space.
