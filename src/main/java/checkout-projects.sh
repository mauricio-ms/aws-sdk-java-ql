rm -rf projects_tmp
mkdir projects_tmp
cd projects_tmp
while read git_repo_url
do
  git clone $git_repo_url
  git_project=$(cut -d'/' -f2 <<<$git_repo_url)
  project=$(cut -d'.' -f1 <<<$git_project)
  cd $project
  git checkout dev
  cd ..
done
