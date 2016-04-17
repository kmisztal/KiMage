# Contributing to KiMage

These guidelines are meant to be a living document that should be changed and adapted as needed.
 
## General Workflow

1. Before starting to work on a feature or a fix, make sure that there is an issue for you on the [GitHub](https://github.com/kmisztal/KiMage/issues). If not, create it first.
2. You should always perform your work in a Git feature branch. The branch name should include issue number and descriptive name that explains its intent.
3. When the feature or fix is completed you should open a [Pull Request](https://help.github.com/articles/using-pull-requests) on the GitHub.
4. The Pull Request should be reviewed by other maintainers.
5. During the code review you should respond to all comments. Leaving comments unanswered is considered rude. 
6. After the review you should fix all issues (pushing a new commit for new review etc.), iterating until the reviewers do not write that everything is good.
7. Once the code has passed review the Pull Request can be merged into the master branch. When the branch conflicts with its merge target, do **not** merge the target branch into your feature branch. Instead rebase your branch onto the target branch. Merges complicate the git history.

## Creating Commits

* Every commit should be able to be used in isolation, cherry picked etc.
* The commit message should be a descriptive sentence what the commit is doing, including the ticket number. For example `[#16] Add flow initialization method`. It should be possible to fully understand what the commit does by just reading this single line.
* It is not acceptable that the commit contains elements that are not required to complete the function described in the commit message or unused code.
* Each commit should be atomic. If you use conjunctions in the commit message it probably means that this commit should be divided into more commits.