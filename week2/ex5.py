import requests

def get_user_repos(username):
    url = f"https://api.github.com/users/{username}/repos"
    response = requests.get(url)
    
    if response.status_code == 200:
        repos = response.json()
        return [
            {
                "name": repo["name"],
                "stars": repo["stargazers_count"],
                "url": repo["html_url"]
            }
            for repo in repos
        ]
    return []

repos = get_user_repos("octocat")
for repo in repos[:5]:
    print(f"⭐ {repo['stars']} - {repo['name']}")