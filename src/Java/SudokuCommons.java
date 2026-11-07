package Java;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class SudokuCommons {
    /*
     Clue Count generation runtimes (n=350) Ryzen 7 7700x:
     23 : 1739.60ms     [   1476.11ms - 2003.09ms  ]
     24 : 377.63ms      [   318.33ms - 436.93ms    ]
     25 : 208.13ms      [   159.91ms - 256.35ms    ]
     26 : 90.18ms       [   71.38ms - 108.99ms     ]
     27 : 39.78ms       [   32.70ms - 46.86ms      ]
     28 : 27.07ms       [   21.17ms - 32.98ms      ]
     29 : 15.80ms       [   13.37ms - 18.24ms      ]
     30 : 9.64ms        [   8.26ms - 11.01ms       ]
     35 : 2.46ms        [   2.22ms - 2.70ms        ]
     40 : 1.70ms        [   1.58ms - 1.82ms        ]
     50 : 1.38ms        [   1.28ms - 1.47ms        ]
     60 : 1.18ms        [   1.10ms - 1.27ms        ]
    */
    static int clueCount = 30;
    static AtomicBoolean autoCandidates = new AtomicBoolean(false);
}