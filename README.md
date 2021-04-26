## [API Live](https://jonathan-github-scrapper.herokuapp.com/)
## [Swagger Documentation](https://jonathan-github-scrapper.herokuapp.com/swagger-ui.html)
### About

This API returns the file count, the total number of lines and the total number of bytes grouped by file extension, of a given public Github repository. As in the example below: 

```
[ 
{ "extension": "java", 
"count": 4, "lines": 360, 
"bytes": 5246 
}, 
{ 
"extension": "xml", 
"count": 2, "lines": 45, 
"bytes": 368 
}, 
… 
] 
```

### How to use it
In order to make a basic request to the API, all you need is POST request to the root path of the application, with a payload such as the following:
```
{
	"repository": "${GITHUB_REPO_NAME}"
}
```
The duration of the first request might take a while, depending on the size and structure of the given repository. After the first request of an especific repository, that response is stored in cache. That way, the next requests for that same repository will take no reprocessing. In order to invalidate that cache and reload the response, you just need to make the same POST request with an additional payload attribute:
```
{
	"repository": "${GITHUB_REPO_NAME}",
        "refresh": "true"
}
```

### Docker Image
This repository contains a Dockerfile through which it is possible to generate a docker image for the API.
#### The image was also made available on a public Docker Hub repo:
[jonathandavidbr/github-scrapper](https://hub.docker.com/repository/docker/jonathandavidbr/github-scrapper)
#### To download the latest image available:
```docker pull jonathandavidbr/github-scrapper:latest```
#### To run the image:
```docker run -p ${LOCAL_PORT}:5000 ${IMAGE_NAME}```

Where *LOCAL_PORT* is the port in which the image must run, and *IMAGE_NAME* is the tag name given to the downloaded image.

### CI/CD with Auto DevOps

This template is compatible with [Auto DevOps](https://docs.gitlab.com/ee/topics/autodevops/).
Auto Deploy of the master branch is enabled through configuration on [Heroku](https://www.heroku.com/).


### Java Spring template project

This project is based on a GitLab [Project Template](https://docs.gitlab.com/ee/gitlab-basics/create-project.html).

### Future Improvements
* Performance improvements on requests and processing
* Auto cache invalidation based on Last Commit info
* Visibility for repositories already stored
* Data persistence
