# Algorithm

1. Read the input bitmaps and count NxN patterns
    1. Optionally augments pattern data with rotations and reflections
2. Create an array of the output dimension (called a "wave"). Each element of the wave represents a state of an NxN region in the output. A state of an NxN region is a superposition of NxN patterns of the input with boolean coefficients. False coefficient means the corresponding pattern is not possible there but true coefficient means the corresponding pattern is not yet forbidden.
    1. IDEA: does this mean store the possible options as {pattern1: true, pattern2: false, ...}?
3. Initialize the wave in a completely unobserved state (all coefficients are true)
4. Repeat:
    1. Find a wave element with the minimal non zero entropy (the number of true coefficients). If there is no such element (all elements have 0 or undefined entropy), stop and go to step 5
    2. Collapse the wave element into a definite state according to its coefficients and the distribution of NxN patterns in the input
    3. Propagate the collapse to the neighboring wave elements
5. By now all waveforms are either in a completely observed state (only one coefficient is true) or in the contradictory state (all coefficients are false). In the former case return the output, in the latter case return an error.