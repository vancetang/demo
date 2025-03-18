# Spring boot test project

Mainly test spring boot, github action...etc related functions that are often seen on the Internet

-   GitHub Action Workflow
    -   add-labels: Add modified labels
    -   cleanup-cache: Clear action cache (there are currently problems with data date conversion)
    -   close-stale-issues-prs: Close old issues/prs
    -   create-release: generate release according to tag
    -   gmail: Send gmail
    -   notify-collaborators: Send github message (repo meal)
    -   sync-branchs-and-notify: main synchronous change to develop and will be notified if it fails
    -   telegram: Send telegram
    -   codeql: static program weakness scanning
    -   test: for testing functions (currently, it is reserved to directly execute java functions, maven clean package...)
    -   translate: translation (cannot test because there is no openai token)
    -   label-pr adds label to PR (label must exist, otherwise an error will occur) (use actions/labeler@v5, please note if it has updated its format)
-   Spring-Boot v3 version
-   Test protection branch (Master), you must PR
-   Test the new local pre-push protection master & check whether there are updates
-   issues return added in English version
-   ~~PR Template test multi-language version~~

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=vancetang/demo&type=Date)](https://star-history.com/#vancetang/demo&Date)
