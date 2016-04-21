# Contributing to KiMage

These guidelines are meant to be a living document that should be changed and adapted as needed.
 
## General Workflow

1. Before starting to work on a feature or a fix, make sure that there is an issue for you on the [GitHub](https://github.com/kmisztal/KiMage/issues). If not - create it first.
2. You should always perform your work in a Git feature branch. The branch name should include issue number and descriptive name that explains its intent.
3. When the feature or fix is completed you should open a [Pull Request](https://help.github.com/articles/using-pull-requests) on the GitHub.
4. The Pull Request should be reviewed by other maintainers.
5. During the code review you should respond to all comments. Leaving comments unanswered is considered rude. 
6. After the review you should fix all issues (pushing a new commit for new review etc.), iterating until the reviewers do not write everything is good.
7. Once the code has passed review the Pull Request can be merged into the master branch. When the branch conflicts with its merge target, do **not** merge the target branch into your feature branch. Instead rebase your branch onto the target branch. Merges complicate the git history.

## Creating Commits

* Every commit should be able to be used in isolation, cherry picked etc.
* The commit message should be a descriptive sentence what the commit is doing, including the ticket number. For example `[#16] Add flow initialization method`. It should be possible to fully understand what the commit does by just reading this single line.
* It is not acceptable the commit contains elements that are not required to complete the function described in the commit message or unused code.
* Each commit should be atomic. If you use conjunctions in the commit message it probably means that this commit should be divided into more commits.

## Code Style

KiMage follows standard Java coding conventions with the additional rules described below.

* Follow the commonly accepted good practices e.g. [The Boy Scout Rule](http://programmer.97things.oreilly.com/wiki/index.php/The_Boy_Scout_Rule), [DRY](http://programmer.97things.oreilly.com/wiki/index.php/Don%27t_Repeat_Yourself) etc.
* Use standard IntelliJ IDEA formatter. Before commit check if the code format is correct. Do not commit unnecessary blank lines, clamps in non standard locations etc.
* Write code (and comments) in English.
* Do not commit commented out code.
* Do not ignore exceptions. You must handle every exception in your code in a principled way. The specific handling varies depending on the case. Do not write e.g. `catch (NumberFormatException e) { }`
* Do not catch or throw generic exception. Catch each exception separately as separate catch blocks after a single try.
* Add message to each exception with explanation of cause with all additional data. For example when the file was not found add path to this file in exception message.
* Use TODO comments (`//TODO exaplanation`) for code that is temporary, a short-term solution, or good-enough but not perfect.
* Do not leave empty blocks of code. If you need to do this, e.g. private constructor in utility classes, leave a comment.
* Pay attention to the correct variable, methods etc. naming. The name must comply with the conventions used in Java. Additionally name cannot be a single letter except very short methods or the counters in simple loops.
* Committed code must compiles and passes all the tests.
* Write thread-safe code. This will allow to run project on multiple machines in the future.
* Unit tests should cover the maximum possible amount of code. Remember about edge-case tests.
* Do not use `null`, optional objects keep in `Optional` wrapper.
* Each interface and abstract class needs [Javadoc](http://www.oracle.com/technetwork/articles/java/index-137868.html).
* Do not use code marked by `@Deprecated` or located in `legacy` package