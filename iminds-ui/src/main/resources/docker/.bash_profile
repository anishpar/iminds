# .bash_profile

# Get the aliases and functions
if [ -f ~/.bashrc ]; then
	. ~/.bashrc
fi

# User specific environment and startup programs

PATH=$PATH:$HOME/.local/bin:$HOME/bin

export PATH


ANT_HOME=$HOME/apache-ant-1.8.1 ; export ANT_HOME
PATH=$ANT_HOME/bin:$PATH ; export PATH

JAVA_HOME=$HOME/jdk1.8.0_65 ; export JAVA_HOME
PATH=$JAVA_HOME/bin:$PATH ; export PATH

MVN_HOME=$HOME/apache-maven-3.3.9 ; export JAVA_HOME
PATH=$MVN_HOME/bin:$PATH ; export PATH