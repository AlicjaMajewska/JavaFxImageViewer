package com.capgemini.starterkit.javafx.dataprovider.data;


public class BookVO {

	    private String title;
//	    private Collection<AuthorVO> authorsVO;
	    private AuthorVO authorVO;
	    private LibraryVO libraryVO;

	    public BookVO() {
	    }

	    public BookVO( String title, AuthorVO authorVO, LibraryVO libraryVO) {
	        this.title = title;
	        this.authorVO = authorVO;
	        this.libraryVO = libraryVO;
	    }
//	    public BookVO( String title, Collection<AuthorVO> authorsVO) {
//	    	this.title = title;
//	    	this.authorsVO = authorsVO;
//	    }

	    public LibraryVO getLibraryVO() {
			return libraryVO;
		}

		public void setLibraryVO(LibraryVO libraryVO) {
			this.libraryVO = libraryVO;
		}

		public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public AuthorVO getAuthorVO() {
	        return authorVO;
	    }

	    public void setAuthorVO(AuthorVO authorVO) {
	        this.authorVO = authorVO;
	    }

		@Override
		public String toString() {
			return "BookVO [title=" + title + ", authorVO=" + authorVO + ", libraryVO=" + libraryVO + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((authorVO == null) ? 0 : authorVO.hashCode());
			result = prime * result + ((libraryVO == null) ? 0 : libraryVO.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BookVO other = (BookVO) obj;
			if (authorVO == null) {
				if (other.authorVO != null)
					return false;
			} else if (!authorVO.equals(other.authorVO))
				return false;
			if (libraryVO == null) {
				if (other.libraryVO != null)
					return false;
			} else if (!libraryVO.equals(other.libraryVO))
				return false;
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;
			return true;
		}


}



